package Valkov.Fishing_Farm_Zasmyano.repository.bungalow;
import Valkov.Fishing_Farm_Zasmyano.domain.model.BungalowReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BungalowBookingRepository extends JpaRepository<BungalowReservation, Long> {
    List<BungalowReservation> findByBungalowIdAndStartDateBetween(
            Long id, LocalDate startDate, LocalDate endDate);

    List<BungalowReservation> findByBungalowIdAndEndDateAfterAndStartDateBefore(
            Long id, LocalDate endDate, LocalDate startDate);
    @Query("SELECT r FROM BungalowReservation r JOIN User u ON r.user.id = u.id WHERE u.email = :email ORDER BY r.createdAt DESC LIMIT 1")
    BungalowReservation findLastReservationByEmail (String email);
}
