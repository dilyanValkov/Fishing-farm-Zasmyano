package Valkov.Fishing_Farm_Zasmyano.repository.bungalow;

import Valkov.Fishing_Farm_Zasmyano.domain.model.BungalowReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BungalowBookingRepository extends JpaRepository<BungalowReservation, Long> {
    List<BungalowReservation> findByBungalowIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long id, LocalDate startDate, LocalDate endDate);
}
