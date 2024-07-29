package com.valkov.fishingfarm.service.impl.user;

import com.valkov.fishingfarm.domain.dto.user.UserRegisterDto;
import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.repository.user.UserRepository;
import com.valkov.fishingfarm.repository.user.UserRoleRepository;
import com.valkov.fishingfarm.service.ReviewService;
import com.valkov.fishingfarm.service.book.BungalowBookService;
import com.valkov.fishingfarm.service.book.FishingBookService;
import com.valkov.fishingfarm.service.impl.UserServiceImpl;
import com.valkov.fishingfarm.service.user.UserUtilService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private UserServiceImpl test;

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private UserUtilService mockUserUtilService;
    @Mock
    private FishingBookService mockFishingBookService;
    @Mock
    private BungalowBookService mockBungalowBookService;
    @Mock
    private ReviewService mockReviewService;
    @Mock
    private UserRoleRepository mockUserRoleRepository;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    void setUp(){
        test = new UserServiceImpl(
                new ModelMapper(),
                mockUserRepository,
                mockPasswordEncoder,
                mockUserUtilService,
                mockFishingBookService,
                mockBungalowBookService,
                mockReviewService,
                mockUserRoleRepository
        );
    }

    @Test
    void testUserRegistration(){

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setFirstName("Ivan");
        userRegisterDto.setLastName("Valkov");
        userRegisterDto.setEmail("dilqnvalkov@gmail.com");
        userRegisterDto.setPhoneNumber("0899363327");
        userRegisterDto.setPassword("111");

        when(mockPasswordEncoder.encode(userRegisterDto.getPassword()))
                .thenReturn(userRegisterDto.getPassword() + userRegisterDto.getPassword());

        test.register(userRegisterDto);

        verify(mockUserRepository).save(userCaptor.capture());

        User actualSavedEntity = userCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        Assertions.assertEquals(userRegisterDto.getFirstName(), actualSavedEntity.getFirstName());
        Assertions.assertEquals(userRegisterDto.getLastName(), actualSavedEntity.getLastName());
        Assertions.assertEquals(userRegisterDto.getEmail(), actualSavedEntity.getEmail());
        Assertions.assertEquals(userRegisterDto.getPassword() +
                userRegisterDto.getPassword(), actualSavedEntity.getPassword());
        Assertions.assertEquals(userRegisterDto.getPhoneNumber(), actualSavedEntity.getPhoneNumber());

    }
}