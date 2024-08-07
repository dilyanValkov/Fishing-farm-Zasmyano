package com.valkov.fishingfarm.service.impl;

import com.valkov.fishingfarm.domain.enums.Attitude;
import com.valkov.fishingfarm.domain.enums.Role;
import com.valkov.fishingfarm.domain.enums.Status;
import com.valkov.fishingfarm.domain.model.Bungalow;
import com.valkov.fishingfarm.domain.model.BungalowReservation;
import com.valkov.fishingfarm.domain.model.FishingReservation;
import com.valkov.fishingfarm.domain.model.FishingSpot;
import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.domain.model.user.UserRole;
import com.valkov.fishingfarm.repository.bungalow.BungalowBookRepository;
import com.valkov.fishingfarm.repository.fishing.FishingBookRepository;
import com.valkov.fishingfarm.repository.user.UserRepository;
import com.valkov.fishingfarm.repository.user.UserRoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {


    private AdminServiceImpl toTest;
    @Mock
    private  UserRepository mockUserRepository;
    @Mock
    private UserRoleRepository mockUserRoleRepository;
    @Mock
    private BungalowBookRepository mockBungalowBookRepository;
    @Mock
    private FishingBookRepository mockFishingBookRepository;
    @Mock
    private EmailService mockEmailService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Captor
    public ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

    @BeforeEach
    void setUp(){
        toTest = new AdminServiceImpl(
                mockUserRepository,
                mockUserRoleRepository,
                mockBungalowBookRepository,
                mockFishingBookRepository,
                mockEmailService
        );
    }

    @Test
    void testSetUserRole_to_ADMIN(){

        List<UserRole> userRoles = new ArrayList<>();
        UserRole userRole = new UserRole();
        userRole.setRole(Role.ADMIN);
        userRoles.add(userRole);

        User user = user();
        when(mockUserRepository.getReferenceById(1L)).thenReturn(user);

        when(mockUserRoleRepository.findAll()).thenReturn(userRoles);

        toTest.setUserRole(1L,"ADMIN");

        verify(mockUserRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(savedUser.getRoles().getFirst().getRole(), Role.ADMIN);
    }

    @Test
    void testSetUserRole_to_USER(){


        User user = user();
        when(mockUserRepository.getReferenceById(1L)).thenReturn(user);


        toTest.setUserRole(1L,"USER");

        verify(mockUserRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(savedUser.getRoles().getFirst().getRole(), Role.USER);
    }

    @Test
    void testSetUserAttitude(){
        User user = user();
        when(mockUserRepository.getReferenceById(1L)).thenReturn(user);
        Assertions.assertEquals(user.getAttitude(), Attitude.GOOD);
        toTest.setUserAttitude(1L,"BAD");
        Assertions.assertEquals(user.getAttitude(), Attitude.BAD);
    }

    @Test
    void testSetBookBungalowStatus(){
        BungalowReservation bungalowReservation = bungalowReservation();
        when(mockBungalowBookRepository.getReferenceById(1L)).thenReturn(bungalowReservation);
        Assertions.assertEquals(bungalowReservation.getStatus(), Status.UNCONFIRMED);
        toTest.setBookBungalowStatus(1L, "REJECTED");
        verify(mockBungalowBookRepository).save(bungalowReservation);
        Assertions.assertEquals(bungalowReservation.getStatus(), Status.REJECTED);
    }

    @Test
    void testDeleteBookBungalowById(){
        toTest.deleteBookBungalowById(1L);
        verify(mockBungalowBookRepository, times(1)).deleteById(1L);
        verify(mockBungalowBookRepository).deleteById(captor.capture());
        Assertions.assertEquals(1L,captor.getValue());
    }

    @Test
    void testSetBookFishingStatus(){
        FishingReservation fishingReservation = fishingReservation();
        when(mockFishingBookRepository.getReferenceById(1L)).thenReturn(fishingReservation);
        Assertions.assertEquals(fishingReservation.getStatus(),Status.UNCONFIRMED);
        toTest.setBookFishingStatus(1L,"CONFIRMED");
        verify(mockFishingBookRepository).save(fishingReservation);
        Assertions.assertEquals(fishingReservation.getStatus(),Status.CONFIRMED);
    }

    @Test
    void testDeleteBookFishingById(){
        toTest.deleteBookFishingById(1L);
        verify(mockFishingBookRepository, times(1)).deleteById(1L);
        verify(mockFishingBookRepository).deleteById(captor.capture());
        Assertions.assertEquals(1L,captor.getValue());
    }

    private User user(){
        User user = new User();
        List<UserRole> userRoles = new ArrayList<>();
        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);
        userRoles.add(userRole);

        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setAttitude(Attitude.GOOD);
        user.setRoles(userRoles);
        user.setEmail("ivan.ivanov@gmail.com");
        user.setPhoneNumber("0899363327");
        user.setPassword("111");
        return user;
    }

    private BungalowReservation bungalowReservation(){
        Bungalow bungalow = new Bungalow();
        bungalow.setCapacity(2);
        bungalow.setId(1L);
        bungalow.setPrice(new BigDecimal(80));

        BungalowReservation bungalowReservation = new BungalowReservation();
        bungalowReservation.setBungalow(bungalow);
        bungalowReservation.setId(1L);
        bungalowReservation.setStatus(Status.UNCONFIRMED);
        bungalowReservation.setUser(user());
        bungalowReservation.setCreatedAt(LocalDateTime.now());
     return bungalowReservation;
    }

    private FishingReservation fishingReservation(){
        FishingReservation fishingReservation = new FishingReservation();
        fishingReservation.setStatus(Status.UNCONFIRMED);
        fishingReservation.setFishingSpot(new FishingSpot());
        fishingReservation.setId(1L);
        fishingReservation.setUser(user());
        return fishingReservation;
    }
}
