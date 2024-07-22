package Valkov.Fishing_Farm_Zasmyano.repository.user;

import Valkov.Fishing_Farm_Zasmyano.domain.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
