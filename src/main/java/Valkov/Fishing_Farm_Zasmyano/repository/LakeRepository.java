package Valkov.Fishing_Farm_Zasmyano.repository;
import Valkov.Fishing_Farm_Zasmyano.domain.model.Lake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LakeRepository extends JpaRepository<Lake, Long> {
    Lake findByName (String name);
}
