package Valkov.Fishing_Farm_Zasmyano.service.impl;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookFishingDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookInfoFishingDto;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import Valkov.Fishing_Farm_Zasmyano.domain.model.FishingReservation;
import Valkov.Fishing_Farm_Zasmyano.domain.model.FishingSpot;
import Valkov.Fishing_Farm_Zasmyano.domain.model.User;
import Valkov.Fishing_Farm_Zasmyano.repository.fishing.FishingBookRepository;
import Valkov.Fishing_Farm_Zasmyano.repository.fishing.FishingSpotRepository;
import Valkov.Fishing_Farm_Zasmyano.service.FishingBookService;
import Valkov.Fishing_Farm_Zasmyano.service.UserUtilService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        List<FishingReservation> existingReservations = fishingBookRepository.findByFishingSpot_IdAndEndDateAfterAndStartDateBeforeAndFishingHours(
                number, dto.getStartDate(), dto.getEndDate(), dto.getFishingHours()
        );
        List<FishingReservation> conflicts = fishingBookRepository.findByFishingSpot_IdAndStartDateBetweenAndFishingHours(
                number, dto.getStartDate(), dto.getEndDate(), dto.getFishingHours()
        );

        if (!existingReservations.isEmpty() || !conflicts.isEmpty()){
            return false;
        }

        FishingSpot fishingSpot = byId.get();

        FishingReservation reservation = modelMapper.map(dto, FishingReservation.class);

        reservation.setStatus(Status.НЕПОТЪВРДЕНА);
        reservation.setUser(user);
        reservation.setFishingSpot(fishingSpot);
        reservation.calculateTotalPrice();
        fishingBookRepository.save(reservation);
        return true;
    }

    @Override
    public BookInfoFishingDto getBookInfoFishingDto() {
        return null;
    }
}
