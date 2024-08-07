package com.valkov.fishingfarm.service.impl;

import com.valkov.fishingfarm.domain.dto.user.UserChangeInfoDto;
import com.valkov.fishingfarm.domain.dto.user.UserChangePasswordDto;
import com.valkov.fishingfarm.domain.dto.user.UserRegisterDto;
import com.valkov.fishingfarm.domain.enums.Attitude;
import com.valkov.fishingfarm.domain.enums.Role;
import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.domain.model.user.UserRole;
import com.valkov.fishingfarm.repository.user.UserRepository;
import com.valkov.fishingfarm.repository.user.UserRoleRepository;
import com.valkov.fishingfarm.service.ReviewService;
import com.valkov.fishingfarm.service.book.BungalowBookService;
import com.valkov.fishingfarm.service.book.FishingBookService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private UserServiceImpl toTest;

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
    void setUp() {
        toTest = new UserServiceImpl(
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
    void testUserRegistration() {

        UserRegisterDto userRegisterDto = userRegisterDto();

        when(mockPasswordEncoder.encode(userRegisterDto.getPassword()))
                .thenReturn(userRegisterDto.getPassword() + userRegisterDto.getPassword());

        toTest.register(userRegisterDto);

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

    @Test
    void testUserFullName() {
        User user = user();
        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        Assertions.assertEquals("Ivan Ivanov", toTest.userFullName(user.getId()));
    }

    @Test
    void testIsEmailUnique() {
        User user = user();
        when(mockUserRepository.existsByEmail(user.getEmail())).thenReturn(true);
        Assertions.assertFalse(toTest.isEmailUnique(user.getEmail()));
    }

    @Test
    void testIsPhoneNumberUnique() {
        User user = user();
        when(mockUserRepository.existsByPhoneNumber(user.getPhoneNumber())).thenReturn(true);
        Assertions.assertFalse(toTest.isPhoneNumberUnique(user.getPhoneNumber()));
    }

    @Test()
    void testUpdateUser() {
        User user = user();
        UserChangeInfoDto dto = userChangeInfoDto();
        User currentUser = new User();

        when(mockUserUtilService.getCurrentUser()).thenReturn(currentUser);
        currentUser.setId(1L);
        when(mockUserRepository.getReferenceById(currentUser.getId())).thenReturn(user);


        toTest.updateUser(dto);

        Assertions.assertEquals(user.getFirstName(), dto.getFirstName());
        Assertions.assertEquals(user.getLastName(), dto.getLastName());
        Assertions.assertEquals(user.getPhoneNumber(), dto.getPhoneNumber());

    }

    @Test
    void testChangePassword() {
        User user = user();
        UserChangePasswordDto dto = userChangePasswordDto();
        User currentUser = new User();

        when(mockUserUtilService.getCurrentUser()).thenReturn(currentUser);
        currentUser.setId(1L);

        when(mockUserRepository.getReferenceById(currentUser.getId())).thenReturn(user);

        when(mockPasswordEncoder.encode(dto.getNewPassword())).thenReturn(dto.getNewPassword() + dto.getNewPassword());
        toTest.changePassword(dto);

        verify(mockUserRepository).saveAndFlush(userCaptor.capture());

        User actualSavedEntity = userCaptor.getValue();
        Assertions.assertEquals(actualSavedEntity.getPassword(), dto.getNewPassword() + dto.getNewPassword());
    }


    @Test
    void testFindAll() {
        List<User> users = new ArrayList<>();
        Assertions.assertEquals(toTest.findAll().size(), 0);
        users.add(user());
        users.add(user());
        when(mockUserRepository.findAll()).thenReturn(users);
        Assertions.assertEquals(toTest.findAll().size(), 2);
    }


    private static UserRegisterDto userRegisterDto() {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setFirstName("Ivan");
        userRegisterDto.setLastName("Ivanov");
        userRegisterDto.setEmail("dilqnvalkov@gmail.com");
        userRegisterDto.setPhoneNumber("0899363327");
        userRegisterDto.setPassword("111");
        return userRegisterDto;
    }

    private static User user() {
        User user = new User();
        UserRole userRole = new UserRole();
        userRole.setRole(Role.ADMIN);
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setAttitude(Attitude.GOOD);
        user.setRoles(List.of(userRole));
        user.setEmail("ivan.ivanov@gmail.com");
        user.setPhoneNumber("0899363327");
        user.setPassword("111");
        return user;
    }

    private static UserChangeInfoDto userChangeInfoDto() {
        UserChangeInfoDto dto = new UserChangeInfoDto();

        dto.setFirstName("Petko");
        dto.setLastName("Petrov");
        dto.setPhoneNumber("0899363326");
        return dto;
    }

    private static UserChangePasswordDto userChangePasswordDto() {
        UserChangePasswordDto dto = new UserChangePasswordDto();

        dto.setOldPassword("111");
        dto.setConfirmPassword("222");
        dto.setNewPassword("222");

        return dto;
    }
}