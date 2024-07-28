package Valkov.Fishing_Farm_Zasmyano.repository.bungalow;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import Valkov.Fishing_Farm_Zasmyano.domain.model.BungalowReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BungalowBookRepository extends JpaRepository<BungalowReservation, Long> {
    List<BungalowReservation> findByBungalowIdAndStartDateBetweenAndStatusIsIn(
            Long id, LocalDate startDate, LocalDate endDate,List<Status> statuses);

    List<BungalowReservation> findByBungalowIdAndEndDateAfterAndStartDateBeforeAndStatusIsIn(
            Long id, LocalDate endDate, LocalDate startDate,List<Status> statuses);

    @Query("SELECT r FROM BungalowReservation r JOIN User u ON r.user.id = u.id WHERE u.email = :email ORDER BY r.createdAt DESC")
    List<BungalowReservation> findAllByEmail(String email);

    void deleteAllByUser_Id(Long id);

    void deleteAllByStatus(Status status);
}
