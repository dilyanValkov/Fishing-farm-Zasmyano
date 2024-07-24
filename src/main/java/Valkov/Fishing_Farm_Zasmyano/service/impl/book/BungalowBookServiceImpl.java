package Valkov.Fishing_Farm_Zasmyano.service.impl.book;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.bungalow.BookBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.bungalow.BookInfoBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import Valkov.Fishing_Farm_Zasmyano.domain.model.Bungalow;
import Valkov.Fishing_Farm_Zasmyano.domain.model.BungalowReservation;
import Valkov.Fishing_Farm_Zasmyano.domain.model.user.User;
import Valkov.Fishing_Farm_Zasmyano.repository.bungalow.BungalowBookRepository;
import Valkov.Fishing_Farm_Zasmyano.repository.bungalow.BungalowRepository;
import Valkov.Fishing_Farm_Zasmyano.service.book.BungalowBookService;
import Valkov.Fishing_Farm_Zasmyano.service.impl.EmailService;
import Valkov.Fishing_Farm_Zasmyano.service.user.UserUtilService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public boolean book(BookBungalowDto dto) throws MessagingException {
        User user = userUtilService.getCurrentUser();

        Optional<Bungalow> byId = bungalowRepository.findById(dto.getNumber());

        if (byId.isEmpty()){
            return false;
        }

        List<BungalowReservation> existingReservations = bungalowBookRepository
                .findByBungalowIdAndStartDateBetween(
                        dto.getNumber(), dto.getStartDate(), dto.getEndDate());
        List<BungalowReservation> conflicts = bungalowBookRepository.findByBungalowIdAndEndDateAfterAndStartDateBefore(dto.getNumber(),
                        dto.getEndDate(), dto.getStartDate());

        if (!existingReservations.isEmpty()||!conflicts.isEmpty()){
            return false;
        }
        Bungalow bungalow = byId.get();

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
