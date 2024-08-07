package com.valkov.fishingfarm.web;

import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.repository.user.UserRepository;
import com.valkov.fishingfarm.testutils.UserTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final String TEST_USER_EMAIL = "nikolay.valkov@gmail.com";
    private static final String TEST_REGISTRATION_EMAIL = "georgi_i@gmail.com";
    private static final String TEST_REGISTRATION_FIRST_NAME = "Georgi";
    private static final String TEST_REGISTRATION_LAST_NAME = "Ivanov";
    private static final String TEST_REGISTRATION_PASSWORD = "111";
    private static final String TEST_REGISTRATION_PHONE_NUMBER = "0899363324";

    private static final String TEST_CHANGED_FIRST_NAME = "Ivan";
    private static final String TEST_CHANGED_LAST_NAME = "Petrov";
    private static final String TEST_CHANGED_PHONE_NUMBER = "0899363326";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserTestData userTestData;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;


    @BeforeEach
    void setUp() {
        testUser = userTestData.createTestAdmin(TEST_USER_EMAIL, "0899363327");
    }

    @AfterEach
    void tearDown() {
        userTestData.cleanUp();
    }

    @Test
    void testRegistration() throws Exception {
        mockMvc.perform(post("/register")
                        .param("email", TEST_REGISTRATION_EMAIL)
                        .param("firstName", TEST_REGISTRATION_FIRST_NAME)
                        .param("lastName", TEST_REGISTRATION_LAST_NAME)
                        .param("password", TEST_REGISTRATION_PASSWORD)
                        .param("phoneNumber", TEST_REGISTRATION_PHONE_NUMBER)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        Optional<User> optionalUser = userRepository.findByEmail(TEST_REGISTRATION_EMAIL);

        Assertions.assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        Assertions.assertEquals(TEST_REGISTRATION_EMAIL, user.getEmail());
        Assertions.assertEquals(TEST_REGISTRATION_FIRST_NAME, user.getFirstName());
        Assertions.assertEquals(TEST_REGISTRATION_LAST_NAME, user.getLastName());
        Assertions.assertEquals(TEST_REGISTRATION_PHONE_NUMBER, user.getPhoneNumber());

        Assertions.assertTrue(passwordEncoder.matches(TEST_REGISTRATION_PASSWORD, user.getPassword()));
    }

    @Test
    @WithMockUser(username = "dilqnvalkov@gmail.com", roles = {"USER", "ADMIN"})
    void testDeleteAdminUser() throws Exception {
        userTestData.createTestUser("dilqnvalkov@gmail.com", "0899465328");
        mockMvc.perform(delete("/user/delete/{id}", testUser.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = TEST_USER_EMAIL)
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/delete/{id}", testUser.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = TEST_USER_EMAIL)
    void testUpdateUser() throws Exception {

        mockMvc.perform(post("/user/update")
                        .param("firstName", TEST_CHANGED_FIRST_NAME)
                        .param("lastName", TEST_CHANGED_LAST_NAME)
                        .param("phoneNumber", TEST_CHANGED_PHONE_NUMBER)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));

        Optional<User> optionalUser = userRepository.findByEmail(TEST_USER_EMAIL);

        Assertions.assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        Assertions.assertEquals(TEST_CHANGED_FIRST_NAME, user.getFirstName());
        Assertions.assertEquals(TEST_CHANGED_LAST_NAME, user.getLastName());
        Assertions.assertEquals(TEST_CHANGED_PHONE_NUMBER, user.getPhoneNumber());
    }


    @Test
    @WithMockUser(username = TEST_USER_EMAIL)
    void testChangeUserPassword() throws Exception {
        mockMvc.perform(post("/user/change-password")
                        .param("oldPassword", "111")
                        .param("newPassword", "222")
                        .param("confirmPassword", "222")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));
        Optional<User> optionalUser = userRepository.findByEmail(TEST_USER_EMAIL);

        Assertions.assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        Assertions.assertTrue(passwordEncoder.matches("222", user.getPassword()));
    }

}
