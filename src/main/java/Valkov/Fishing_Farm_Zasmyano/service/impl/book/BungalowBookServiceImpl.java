package Valkov.Fishing_Farm_Zasmyano.service.impl.book;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.bungalow.BookBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.bungalow.BookInfoBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import Valkov.Fishing_Farm_Zasmyano.domain.model.Bungalow;
import Valkov.Fishing_Farm_Zasmyano.domain.model.BungalowReservation;
import Valkov.Fishing_Farm_Zasmyano.domain.model.User;
import Valkov.Fishing_Farm_Zasmyano.repository.bungalow.BungalowBookingRepository;
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
    private final BungalowBookingRepository bungalowBookingRepository;
    private final BungalowRepository bungalowRepository;
    private final ModelMapper modelMapper;
    private final UserUtilService userUtilService;
    private final EmailService emailService;

    @Override
    public List<Bungalow> allBungalows(){
        return bungalowRepository.findAll();
    }

    @Override
    public List<BookInfoBungalowDto> getAllBookings() {
        User user = userUtilService.getCurrentUser();
        String email = user.getEmail();

        List<BungalowReservation> allByEmail = this.bungalowBookingRepository.findAllByEmail(email);
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
    public boolean book(BookBungalowDto dto) throws MessagingException {
        User user = userUtilService.getCurrentUser();

        Optional<Bungalow> byId = bungalowRepository.findById(dto.getNumber());

        if (byId.isEmpty()){
            return false;
        }

        List<BungalowReservation> existingReservations = bungalowBookingRepository
                .findByBungalowIdAndStartDateBetween(
                        dto.getNumber(), dto.getStartDate(), dto.getEndDate());
        List<BungalowReservation> conflicts = bungalowBookingRepository.findByBungalowIdAndEndDateAfterAndStartDateBefore(dto.getNumber(),
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
        bungalowBookingRepository.save(reservation);
        emailService.sendSimpleEmail(user.getEmail(), reservation.emailContent(), reservation.toBeConfirmed());
        return true;
    }

}
