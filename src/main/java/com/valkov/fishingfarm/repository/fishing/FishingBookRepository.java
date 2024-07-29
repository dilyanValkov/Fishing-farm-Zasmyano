package com.valkov.fishingfarm.repository.fishing;
import com.valkov.fishingfarm.domain.enums.Status;
import com.valkov.fishingfarm.domain.model.FishingReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FishingBookRepository extends JpaRepository<FishingReservation, Long> {

    List<FishingReservation> findByFishingSpot_IdAndEndDateAfterAndStartDateBeforeAndStatusIsIn(
            Long number, LocalDate startDate, LocalDate endDate, List<Status> statuses);
    List<FishingReservation> findByFishingSpot_IdAndStartDateBetweenAndStatusIsIn(
            Long number, LocalDate startDate, LocalDate endDate, List<Status> statuses);

    @Query("SELECT r FROM FishingReservation r JOIN User u ON r.user.id = u.id WHERE u.email = :email ORDER BY r.createdAt DESC")
    List<FishingReservation> findAllByEmail(String email);

    void deleteAllByUser_id(Long userId);

    void deleteAllByStatus(Status status);
}
