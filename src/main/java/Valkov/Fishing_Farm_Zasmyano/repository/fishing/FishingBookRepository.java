package Valkov.Fishing_Farm_Zasmyano.repository.fishing;
import Valkov.Fishing_Farm_Zasmyano.domain.model.FishingReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FishingBookRepository extends JpaRepository<FishingReservation, Long> {

    List<FishingReservation> findByFishingSpot_IdAndEndDateAfterAndStartDateBefore(
            Long number, LocalDate startDate, LocalDate endDate);
    List<FishingReservation> findByFishingSpot_IdAndStartDateBetween(
            Long number, LocalDate startDate, LocalDate endDate);

    @Query("SELECT r FROM FishingReservation r JOIN User u ON r.user.id = u.id WHERE u.email = :email ORDER BY r.createdAt DESC")
    List<FishingReservation> findAllByEmail(String email);

    void deleteAllByUser_id(Long userId);
}
