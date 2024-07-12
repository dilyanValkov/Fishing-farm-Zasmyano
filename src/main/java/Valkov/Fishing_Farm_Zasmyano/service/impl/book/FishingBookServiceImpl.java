package Valkov.Fishing_Farm_Zasmyano.service.impl.book;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.fishing.BookFishingDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.fishing.BookInfoFishingDto;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import Valkov.Fishing_Farm_Zasmyano.domain.model.FishingReservation;
import Valkov.Fishing_Farm_Zasmyano.domain.model.FishingSpot;
import Valkov.Fishing_Farm_Zasmyano.domain.model.User;
import Valkov.Fishing_Farm_Zasmyano.repository.fishing.FishingBookRepository;
import Valkov.Fishing_Farm_Zasmyano.repository.fishing.FishingSpotRepository;
import Valkov.Fishing_Farm_Zasmyano.service.book.FishingBookService;
import Valkov.Fishing_Farm_Zasmyano.service.user.UserUtilService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FishingBookServiceImpl implements FishingBookService {

    private final UserUtilService userUtilService;
    private final FishingBookRepository fishingBookRepository;
    private final FishingSpotRepository fishingSpotRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean book(BookFishingDto dto) {
        User user = userUtilService.getCurrentUser();
        Long number = dto.getFishingSpot().getNumber();
        Optional<FishingSpot> byId = fishingSpotRepository.findById(number);

        if (byId.isEmpty()){
            return false;
        }
        List<FishingReservation> existingReservations = fishingBookRepository.findByFishingSpot_IdAndEndDateAfterAndStartDateBefore(
                number, dto.getStartDate(), dto.getEndDate());
        List<FishingReservation> conflicts = fishingBookRepository.findByFishingSpot_IdAndStartDateBetween(
                number, dto.getStartDate(), dto.getEndDate());

        if (!existingReservations.isEmpty() || !conflicts.isEmpty()){
            return false;
        }

        FishingSpot fishingSpot = byId.get();

        FishingReservation reservation = modelMapper.map(dto, FishingReservation.class);

        reservation.setStatus(Status.UNCONFIRMED);
        reservation.setUser(user);
        reservation.setFishingSpot(fishingSpot);
        reservation.calculateTotalPrice();
        fishingBookRepository.save(reservation);
        return true;
    }

    @Override
    public List<BookInfoFishingDto> getAllBookings() {
        User user = userUtilService.getCurrentUser();
        String email = user.getEmail();
        List<FishingReservation> allByEmail = this.fishingBookRepository.findAllByEmail(email);
        List<BookInfoFishingDto> dtos = new ArrayList<>();
        for (FishingReservation reservation : allByEmail) {
            BookInfoFishingDto mapped = modelMapper.map(reservation, BookInfoFishingDto.class);
            mapped.setSpotNumber(reservation.getFishingSpot().getId());
            mapped.setReservationNumber(reservation.getId());
            mapped.setType(reservation.getFishingHours().getText());
            dtos.add(mapped);
        }
        return dtos;
    }
}
