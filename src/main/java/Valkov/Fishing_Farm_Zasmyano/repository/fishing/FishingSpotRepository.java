package Valkov.Fishing_Farm_Zasmyano.repository.fishing;
import Valkov.Fishing_Farm_Zasmyano.domain.model.FishingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FishingSpotRepository extends JpaRepository<FishingSpot, Long> {

}
