package Valkov.Fishing_Farm_Zasmyano.repository.fishing;

import Valkov.Fishing_Farm_Zasmyano.domain.enums.FishingHours;
import Valkov.Fishing_Farm_Zasmyano.domain.model.FishingReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FishingBookRepository extends JpaRepository<FishingReservation, Long> {

    List<FishingReservation> findByFishingSpot_IdAndEndDateAfterAndStartDateBeforeAndFishingHours(
            Long number, LocalDate startDate, LocalDate endDate, FishingHours fishingHours
    );
    List<FishingReservation> findByFishingSpot_IdAndStartDateBetweenAndFishingHours(
            Long number, LocalDate startDate, LocalDate endDate, FishingHours fishingHours
    );
}
