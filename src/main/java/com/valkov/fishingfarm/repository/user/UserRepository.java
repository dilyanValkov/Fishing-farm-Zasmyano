package com.valkov.fishingfarm.repository.user;

import com.valkov.fishingfarm.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional <User> findByEmail(String email);

    User getByEmail(String email);
}
