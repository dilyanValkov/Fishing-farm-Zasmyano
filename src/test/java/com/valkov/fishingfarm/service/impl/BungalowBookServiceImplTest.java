package com.valkov.fishingfarm.service.impl;

import com.valkov.fishingfarm.domain.dto.bungalow.BookBungalowDto;
import com.valkov.fishingfarm.domain.dto.bungalow.BookInfoBungalowDto;
import com.valkov.fishingfarm.domain.enums.Attitude;
import com.valkov.fishingfarm.domain.enums.Role;
import com.valkov.fishingfarm.domain.enums.Status;
import com.valkov.fishingfarm.domain.model.Bungalow;
import com.valkov.fishingfarm.domain.model.BungalowReservation;
import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.domain.model.user.UserRole;
import com.valkov.fishingfarm.repository.bungalow.BungalowBookRepository;
import com.valkov.fishingfarm.repository.bungalow.BungalowRepository;
import com.valkov.fishingfarm.service.user.UserUtilService;
import jakarta.mail.MessagingException;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BungalowBookServiceImplTest {

    private BungalowBookServiceImpl toTest;
    @Mock
    private UserUtilService mockUserUtilService;

    @Mock
    private BungalowRepository mockBungalowRepository;

    @Mock(strictness = Mock.Strictness.LENIENT)
    private BungalowBookRepository mockBungalowBookRepository;

    @Mock
    private EmailService mockEmailService;

    @Captor
    public ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

    @Captor
    private ArgumentCaptor<BungalowReservation> reservationCaptor;

    @BeforeEach
    void setUp() {
        toTest = new BungalowBookServiceImpl(
                mockBungalowBookRepository,
                mockBungalowRepository,
                new ModelMapper(),
                mockUserUtilService,
                mockEmailService

        );
    }

    @Test
    void testAllBungalows() {
        when(mockBungalowRepository.findAll()).thenReturn(getTestBungalows());
        List<Bungalow> actualBungalows = toTest.allBungalows();
        Assertions.assertEquals(1, actualBungalows.size());
    }

    @Test
    void testGetAllUserBookings() {
        User user = user();
        BookInfoBungalowDto bookInfoBungalowDto = bookInfoBungalowDto();
        when(mockUserUtilService.getCurrentUser()).thenReturn(user);
        Assertions.assertEquals(toTest.getAllUserBookings().size(), 0);
        List<BungalowReservation> bungalowReservations = List.of(bungalowReservation());
        toTest.getAllUserBookings();
        when(mockBungalowBookRepository.findAllByEmail("ivan.valkov@gmail.com")).thenReturn(bungalowReservations);

        Assertions.assertEquals(toTest.getAllUserBookings().size(), 1);
        Assertions.assertEquals(bungalowReservations.getFirst().getBungalow().getId(), bookInfoBungalowDto.getBungalowNumber());
        Assertions.assertEquals(bungalowReservations.getFirst().getId(), bookInfoBungalowDto.getReservationNumber());
    }

    @Test
    void testDeleteReservations() {
        toTest.deleteReservations(user().getId());
        verify(mockBungalowBookRepository).deleteAllByUser_Id(captor.capture());
        Assertions.assertEquals(user().getId(), captor.getValue());
    }

    @Test
    void testGetAllBookings() {
        BookInfoBungalowDto bookInfoBungalowDto = bookInfoBungalowDto();
        List<BungalowReservation> bungalowReservations = List.of(bungalowReservation());
        when(mockBungalowBookRepository.findAll()).thenReturn(bungalowReservations);
        Assertions.assertEquals(toTest.getAllBookings().size(), 1);
        Assertions.assertEquals(bungalowReservations.getFirst().getBungalow().getId(), bookInfoBungalowDto.getBungalowNumber());
        Assertions.assertEquals(bungalowReservations.getFirst().getId(), bookInfoBungalowDto.getReservationNumber());
    }

    @Test
    void testBook_If_booking_Exist() throws MessagingException {
        User user = user();
        Bungalow bungalow = bungalow();
        BungalowReservation reservation = bungalowReservation();
        BookBungalowDto bookBungalowDto = bookBungalowDto();
        List<BungalowReservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        bungalow.setReservations(reservations);
        user.setBungalowReservations(reservations);
        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.UNCONFIRMED);
        statuses.add(Status.CONFIRMED);

        when(mockUserUtilService.getCurrentUser()).thenReturn(user);

        when(mockBungalowRepository.findById(1L)).thenReturn(Optional.of(bungalow));

        when(mockBungalowBookRepository
                .findByBungalowIdAndEndDateAfterAndStartDateBeforeAndStatusIsIn(bookBungalowDto.getNumber(),
                        bookBungalowDto.getEndDate(),bookBungalowDto.getStartDate(),statuses))
                .thenReturn(new ArrayList<>());

        when(mockBungalowBookRepository
                .findByBungalowIdAndStartDateBetweenAndStatusIsIn(bookBungalowDto.getNumber(),
                        bookBungalowDto.getEndDate(),bookBungalowDto.getStartDate(),statuses))
                .thenReturn(reservations);

        Assertions.assertFalse(toTest.book(bookBungalowDto));
    }

    @Test
    void testBook_If_booking_Not_Exist() throws MessagingException {
        User user = user();
        Bungalow bungalow = bungalow();
        BungalowReservation reservation = bungalowReservation();
        BookBungalowDto bookBungalowDto = bookBungalowDto();
        List<BungalowReservation> reservations = new ArrayList<>();

        bungalow.setReservations(reservations);
        user.setBungalowReservations(reservations);

        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.UNCONFIRMED);
        statuses.add(Status.CONFIRMED);

        when(mockUserUtilService.getCurrentUser()).thenReturn(user);

        when(mockBungalowRepository.findById(1L)).thenReturn(Optional.of(bungalow));

        when(mockBungalowBookRepository
                .findByBungalowIdAndEndDateAfterAndStartDateBeforeAndStatusIsIn(reservation.getId(),
                        reservation.getEndDate(),reservation.getStartDate(),statuses))
                .thenReturn(new ArrayList<>());

        when(mockBungalowBookRepository
                .findByBungalowIdAndStartDateBetweenAndStatusIsIn(reservation.getId(),
                        reservation.getEndDate(),reservation.getStartDate(),statuses))
                .thenReturn(new ArrayList<>());

        Assertions.assertTrue(toTest.book(bookBungalowDto));

        verify(mockBungalowBookRepository).save(reservationCaptor.capture());
        BungalowReservation actualReservation = reservationCaptor.getValue();

        Assertions.assertNotNull(actualReservation);
        Assertions.assertEquals(actualReservation.getBungalow().getId(),bungalow().getId());
        Assertions.assertEquals(actualReservation.getEndDate(),reservation.getEndDate());
        Assertions.assertEquals(actualReservation.getStartDate(),reservation.getStartDate());

    }


    private Bungalow bungalow() {
        Bungalow bungalow = new Bungalow();
        bungalow.setPrice(new BigDecimal(80));
        bungalow.setId(1L);

        bungalow.setCapacity(2);
        return bungalow;
    }

    private User user() {
        User user = new User();
        UserRole userRole = new UserRole();
        userRole.setRole(Role.ADMIN);
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Valkov");
        user.setAttitude(Attitude.GOOD);
        user.setRoles(List.of(userRole));
        user.setEmail("ivan.valkov@gmail.com");
        user.setPhoneNumber("0899363327");
        user.setPassword("111");
        return user;
    }

    private BungalowReservation bungalowReservation() {
        LocalDateTime createdAt = LocalDateTime.of(2024, 8, 2, 12, 13);
        LocalDate startDate = LocalDate.of(2024, 8, 3);
        LocalDate endDate = LocalDate.of(2024, 8, 4);
        BungalowReservation bungalowReservation = new BungalowReservation();
        bungalowReservation.setBungalow(bungalow());
        bungalowReservation.setId(15L);
        bungalowReservation.setStatus(Status.UNCONFIRMED);
        bungalowReservation.setUser(user());
        bungalowReservation.setCreatedAt(createdAt);
        bungalowReservation.setStartDate(startDate);
        bungalowReservation.setEndDate(endDate);

        return bungalowReservation;
    }

    private List<Bungalow> getTestBungalows() {
        return List.of(bungalow());
    }

    private BookInfoBungalowDto bookInfoBungalowDto() {
        BookInfoBungalowDto bookInfoBungalowDto = new BookInfoBungalowDto();

        bookInfoBungalowDto.setBungalowNumber(1L);
        bookInfoBungalowDto.setReservationNumber(15L);
        bookInfoBungalowDto.setStatus(Status.CONFIRMED);
        return bookInfoBungalowDto;
    }

    private BookBungalowDto bookBungalowDto(){
        BookBungalowDto bookBungalowDto = new BookBungalowDto();

        LocalDate startDate = LocalDate.of(2024, 8, 3);
        LocalDate endDate = LocalDate.of(2024, 8, 4);

        bookBungalowDto.setNumber(1L);
        bookBungalowDto.setStartDate(startDate);
        bookBungalowDto.setEndDate(endDate);

        return bookBungalowDto;
    }
}
