package com.valkov.fishingfarm.testutils;

import com.valkov.fishingfarm.domain.enums.Attitude;
import com.valkov.fishingfarm.domain.enums.Role;
import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.domain.model.user.UserRole;
import com.valkov.fishingfarm.repository.user.UserRepository;
import com.valkov.fishingfarm.repository.user.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTestData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public User createTestUser(String email) {
        return createUser(email, List.of(Role.USER));
    }

    public User createTestAdmin(String email) {
        return createUser(email, List.of(Role.ADMIN));
    }

    private User createUser(String email, List<Role> roles) {

        List<UserRole> userRoles = userRoleRepository.findAllByRoleIn(roles);

        User user = new User();
        user.setRoles(userRoles);
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Valkov");
        user.setAttitude(Attitude.GOOD);
        user.setEmail(email);
        user.setPhoneNumber("0899363327");
        user.setPassword("feeffb9ad3e174f61de2dfc5179a9a959799046de5633a8136174c573c4c61d5c30fa955bf075c90c3a17dc2541f114a");

        return userRepository.save(user);
    }

    public void cleanUp() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }
}
