package com.valkov.fishingfarm.testutils;

import com.valkov.fishingfarm.domain.enums.Role;
import com.valkov.fishingfarm.domain.model.user.UserRole;
import com.valkov.fishingfarm.repository.user.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DBInit implements CommandLineRunner {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public void run(String... args) throws Exception {
        List<UserRole> roles = new ArrayList<>();
        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);
        roles.add(userRole);
        UserRole adminRole = new UserRole();
        userRole.setRole(Role.ADMIN);

        if (userRoleRepository.count() == 0) {
            userRoleRepository.saveAll(roles);
        }
    }
}
