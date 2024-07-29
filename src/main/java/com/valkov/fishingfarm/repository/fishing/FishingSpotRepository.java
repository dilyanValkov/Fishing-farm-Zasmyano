package com.valkov.fishingfarm.repository.fishing;
import com.valkov.fishingfarm.domain.model.FishingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FishingSpotRepository extends JpaRepository<FishingSpot, Long> {

}
