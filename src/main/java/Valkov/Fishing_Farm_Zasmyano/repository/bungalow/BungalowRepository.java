package Valkov.Fishing_Farm_Zasmyano.repository.bungalow;

import Valkov.Fishing_Farm_Zasmyano.domain.model.Bungalow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BungalowRepository extends JpaRepository<Bungalow, Long> {
}
