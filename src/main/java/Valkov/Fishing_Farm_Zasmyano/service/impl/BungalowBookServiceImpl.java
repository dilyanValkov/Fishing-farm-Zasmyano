package Valkov.Fishing_Farm_Zasmyano.service.impl;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookInfoBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import Valkov.Fishing_Farm_Zasmyano.domain.model.Bungalow;
import Valkov.Fishing_Farm_Zasmyano.domain.model.BungalowReservation;
import Valkov.Fishing_Farm_Zasmyano.domain.model.User;
import Valkov.Fishing_Farm_Zasmyano.repository.UserRepository;
import Valkov.Fishing_Farm_Zasmyano.repository.bungalow.BungalowBookingRepository;
import Valkov.Fishing_Farm_Zasmyano.repository.bungalow.BungalowRepository;
import Valkov.Fishing_Farm_Zasmyano.service.BungalowBookService;
import Valkov.Fishing_Farm_Zasmyano.service.UserUtilService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class BungalowBookServiceImpl implements BungalowBookService {
    private final BungalowBookingRepository bungalowBookingRepository;
    private final BungalowRepository bungalowRepository;
    private final ModelMapper modelMapper;
    private final UserUtilService userUtilService;

    @Override
    public List<Bungalow> allBungalows(){
        return bungalowRepository.findAll();
    }

    @Override
    public BookInfoBungalowDto getBookInfoBungalowDto() {
        Authentication authentication = userUtilService.getAuthentication();
        String email = authentication.getName();
        BungalowReservation lastReservation =
                this.bungalowBookingRepository.findLastReservationByEmail(email);

        BookInfoBungalowDto mapped = modelMapper.map(lastReservation, BookInfoBungalowDto.class);
        mapped.setBungalowNumber(lastReservation.getBungalow().getId());
        mapped.setReservationNumber(lastReservation.getId());
        return mapped;
    }

    @Override
    public boolean book(BookBungalowDto dto) {
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
        reservation.setStatus(Status.НЕПОТЪВРДЕНА);
        reservation.setUser(user);
        reservation.setBungalow(bungalow);
        reservation.calculateTotalPrice();
        bungalowBookingRepository.save(reservation);
        return true;
    }
}
