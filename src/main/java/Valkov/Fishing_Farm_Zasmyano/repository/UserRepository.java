package Valkov.Fishing_Farm_Zasmyano.repository;

import Valkov.Fishing_Farm_Zasmyano.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
