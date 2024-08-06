package com.valkov.fishingfarm.repository.user;

import com.valkov.fishingfarm.domain.enums.Role;
import com.valkov.fishingfarm.domain.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findAllByRoleIn(List<Role> role);
}
