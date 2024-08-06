package com.valkov.fishingfarm.service.impl;
import com.valkov.fishingfarm.domain.dto.bungalow.BookBungalowDto;
import com.valkov.fishingfarm.domain.dto.bungalow.BookInfoBungalowDto;
import com.valkov.fishingfarm.domain.enums.Status;
import com.valkov.fishingfarm.domain.model.Bungalow;
import com.valkov.fishingfarm.domain.model.BungalowReservation;
import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.repository.bungalow.BungalowBookRepository;
import com.valkov.fishingfarm.repository.bungalow.BungalowRepository;
import com.valkov.fishingfarm.service.book.BungalowBookService;
import com.valkov.fishingfarm.service.user.UserUtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BungalowBookServiceImpl implements BungalowBookService {
    private final BungalowBookRepository bungalowBookRepository;
    private final BungalowRepository bungalowRepository;
    private final ModelMapper modelMapper;
    private final UserUtilService userUtilService;
    private final EmailService emailService;

    @Override
    public List<Bungalow> allBungalows(){
        return bungalowRepository.findAll();
    }

    @Override
    public List<BookInfoBungalowDto> getAllUserBookings() {
        User user = userUtilService.getCurrentUser();
        String email = user.getEmail();

        List<BungalowReservation> allByEmail = this.bungalowBookRepository.findAllByEmail(email);
        List<BookInfoBungalowDto> dtos = new ArrayList<>();

        for (BungalowReservation reservation : allByEmail) {
            BookInfoBungalowDto mapped = modelMapper.map(reservation, BookInfoBungalowDto.class);
            mapped.setReservationNumber(reservation.getId());
            mapped.setBungalowNumber(reservation.getBungalow().getId());
            dtos.add(mapped);
        }
        return dtos;
    }

    @Override
    public void deleteReservations(Long userId) {
        bungalowBookRepository.deleteAllByUser_Id(userId);
    }

    @Override
    public List<BookInfoBungalowDto> getAllBookings() {
        List<BungalowReservation> bookings = bungalowBookRepository.findAll();
        List<BookInfoBungalowDto> dtos = new ArrayList<>();
        for (BungalowReservation booking : bookings) {
            BookInfoBungalowDto mapped = modelMapper.map(booking, BookInfoBungalowDto.class);
            mapped.setUserInfo(booking.getUser().getUserInfo());
            mapped.setReservationNumber(booking.getId());
            mapped.setBungalowNumber(booking.getBungalow().getId());
            dtos.add(mapped);
        }
        return dtos;
    }

    @Override
    @Transactional
    public boolean book(BookBungalowDto dto) {
        User user = userUtilService.getCurrentUser();

        Bungalow bungalow = bungalowRepository.getReferenceById(dto.getNumber());

        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.UNCONFIRMED);
        statuses.add(Status.CONFIRMED);

        List<BungalowReservation> existingReservations = bungalowBookRepository
                .findByBungalowIdAndStartDateBetweenAndStatusIsIn(dto.getNumber(), dto.getStartDate(), dto.getEndDate(), statuses);
        List<BungalowReservation> conflicts = bungalowBookRepository
                .findByBungalowIdAndStartDateBetweenAndStatusIsIn(dto.getNumber(), dto.getEndDate(), dto.getStartDate(),statuses);

        if (!existingReservations.isEmpty()||!conflicts.isEmpty()){
            return false;
        }


        BungalowReservation reservation = modelMapper.map(dto, BungalowReservation.class);
        reservation.setStatus(Status.UNCONFIRMED);
        reservation.setUser(user);
        reservation.setBungalow(bungalow);
        reservation.calculateTotalPrice();
        bungalowBookRepository.save(reservation);
        emailService.sendSimpleEmail(user.getEmail(),
                reservation.emailContent(),
                reservation.statusMessage(reservation.getStatus().toString()));

        return true;
    }

}
