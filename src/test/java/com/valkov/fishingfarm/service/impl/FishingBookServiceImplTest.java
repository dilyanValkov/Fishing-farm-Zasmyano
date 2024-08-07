package com.valkov.fishingfarm.service.impl;

import com.valkov.fishingfarm.domain.dto.fishing.BookFishingDto;
import com.valkov.fishingfarm.domain.dto.fishing.BookInfoFishingDto;
import com.valkov.fishingfarm.domain.enums.*;
import com.valkov.fishingfarm.domain.model.FishingReservation;
import com.valkov.fishingfarm.domain.model.FishingSpot;
import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.domain.model.user.UserRole;
import com.valkov.fishingfarm.repository.fishing.FishingBookRepository;
import com.valkov.fishingfarm.repository.fishing.FishingSpotRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FishingBookServiceImplTest {

    private FishingBookServiceImpl toTest;

    @Mock
    private UserUtilService mockUserUtilService;

    @Mock
    private EmailService mockEmailService;

    @Mock(strictness = Mock.Strictness.LENIENT)
    private FishingBookRepository mockFishingBookRepository;

    @Mock
    private FishingSpotRepository mockFishingSpotRepository;

    @Captor
    private ArgumentCaptor<FishingReservation> reservationCaptor;

    @BeforeEach
    void setUp() {
        toTest = new FishingBookServiceImpl(
                mockUserUtilService,
                mockFishingBookRepository,
                mockFishingSpotRepository,
                new ModelMapper(),
                mockEmailService
        );
    }

    @Test
    void testBook_If_Booking_Exist() {
        User user = user();
        FishingSpot fishingSpot = fishingSpot();
        FishingReservation fishingReservation = fishingReservation();
        BookFishingDto bookFishingDto = bookFishingDto();
        List<FishingReservation> reservations = new ArrayList<>();
        reservations.add(fishingReservation);
        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.UNCONFIRMED);
        statuses.add(Status.CONFIRMED);

        when(mockUserUtilService.getCurrentUser()).thenReturn(user);

        when(mockFishingSpotRepository.getReferenceById(bookFishingDto.getFishingSpot().getNumber()))
                .thenReturn(fishingSpot);

        Assertions.assertNotNull(fishingReservation);

        when(mockFishingBookRepository.findByFishingSpot_IdAndEndDateAfterAndStartDateBeforeAndStatusIsIn(
                bookFishingDto.getFishingSpot().getNumber(), bookFishingDto.getStartDate(), bookFishingDto.getEndDate(), statuses
        )).thenReturn(new ArrayList<>());

        when(mockFishingBookRepository.findByFishingSpot_IdAndStartDateBetweenAndStatusIsIn(bookFishingDto.getFishingSpot().getNumber(), bookFishingDto.getStartDate(), bookFishingDto.getEndDate(), statuses
        )).thenReturn(reservations);

        Assertions.assertFalse(toTest.book(bookFishingDto));
    }

    @Test
    void testBook_If_Booking_Not_Exist() {
        User user = user();
        FishingSpot fishingSpot = fishingSpot();
        FishingReservation fishingReservation = fishingReservation();
        BookFishingDto bookFishingDto = bookFishingDto();

        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.UNCONFIRMED);
        statuses.add(Status.CONFIRMED);

        when(mockUserUtilService.getCurrentUser()).thenReturn(user);

        when(mockFishingSpotRepository.getReferenceById(bookFishingDto.getFishingSpot().getNumber()))
                .thenReturn(fishingSpot);

        Assertions.assertNotNull(fishingReservation);

        when(mockFishingBookRepository.findByFishingSpot_IdAndEndDateAfterAndStartDateBeforeAndStatusIsIn(
                bookFishingDto.getFishingSpot().getNumber(), bookFishingDto.getStartDate(), bookFishingDto.getEndDate(), statuses
        )).thenReturn(new ArrayList<>());

        when(mockFishingBookRepository.findByFishingSpot_IdAndStartDateBetweenAndStatusIsIn(bookFishingDto.getFishingSpot().getNumber(), bookFishingDto.getStartDate(), bookFishingDto.getEndDate(), statuses
        )).thenReturn(new ArrayList<>());

        Assertions.assertTrue(toTest.book(bookFishingDto));

        verify(mockFishingBookRepository).save(reservationCaptor.capture());
        FishingReservation actualReservation = reservationCaptor.getValue();

        Assertions.assertEquals(actualReservation.getFishingSpot().getId(),
                bookFishingDto.getFishingSpot().getNumber());

        Assertions.assertEquals(actualReservation.getEndDate(), bookFishingDto.getEndDate());

    }

    @Test
    void testGetAllUserBookings() {
        User user = user();
        BookInfoFishingDto bookInfoFishingDto = bookInfoFishingDto();

        List<FishingReservation> reservations = new ArrayList<>();
        FishingReservation fishingReservation = fishingReservation();
        reservations.add(fishingReservation);
        when(mockFishingBookRepository.findAllByEmail(user.getEmail())).thenReturn(reservations);

        Assertions.assertEquals(reservations.getFirst().getFishermanCount(), bookInfoFishingDto.getFishermanCount());
        Assertions.assertEquals(reservations.getFirst().getId(), bookInfoFishingDto.getId());

    }


    private FishingSpot fishingSpot() {
        FishingSpot fishingSpot = new FishingSpot();
        fishingSpot.setId(1L);
        fishingSpot.setCapacity(2);
        fishingSpot.setDayPrice(BigDecimal.valueOf(25));
        fishingSpot.setDayAndNightPrice(BigDecimal.valueOf(40));
        return fishingSpot;
    }

    private FishingReservation fishingReservation() {
        FishingReservation fishingReservation = new FishingReservation();

        LocalDate startDate = LocalDate.of(2024, 8, 3);
        LocalDate endDate = LocalDate.of(2024, 8, 4);

        fishingReservation.setFishingSpot(fishingSpot());
        fishingReservation.setStatus(Status.UNCONFIRMED);
        fishingReservation.setId(1L);
        fishingReservation.setFishermanCount(2);
        fishingReservation.setStartDate(startDate);
        fishingReservation.setEndDate(endDate);
        return fishingReservation;
    }

    private User user() {
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

    private BookInfoFishingDto bookInfoFishingDto() {
        BookInfoFishingDto bookInfoFishingDto = new BookInfoFishingDto();

        bookInfoFishingDto.setFishingHours(FishingHours.DAY);
        bookInfoFishingDto.setId(1L);
        bookInfoFishingDto.setFishermanCount(2);
        bookInfoFishingDto.setSpotNumber(1);

        return bookInfoFishingDto;
    }

    private BookFishingDto bookFishingDto() {
        BookFishingDto bookFishingDto = new BookFishingDto();
        LocalDate startDate = LocalDate.of(2024, 8, 3);
        LocalDate endDate = LocalDate.of(2024, 8, 4);
        bookFishingDto.setFishingSpot(FishingSpotNumber.ONE);
        bookFishingDto.setFishingHours(FishingHours.DAY);
        bookFishingDto.setFishermanCount(2);
        bookFishingDto.setEndDate(endDate);
        bookFishingDto.setStartDate(startDate);
        return bookFishingDto;
    }
}
