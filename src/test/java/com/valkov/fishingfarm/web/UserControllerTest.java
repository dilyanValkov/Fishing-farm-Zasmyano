package com.valkov.fishingfarm.web;

import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.repository.user.UserRepository;
import com.valkov.fishingfarm.service.ReviewService;
import com.valkov.fishingfarm.testutils.UserTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserTestData userTestData;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ReviewService reviewService;


    @AfterEach
    void tearDown() {
        userTestData.cleanUp();
    }

    @Test
    void testRegistration() throws Exception {
        mockMvc.perform(post("/register")
                        .param("email", "random@gmail.com")
                        .param("firstName", "Dilan")
                        .param("lastName", "Valkov")
                        .param("password", "111")
                        .param("phoneNumber", "0899363324")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        Optional<User> optionalUser = userRepository.findByEmail("random@gmail.com");

        Assertions.assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        Assertions.assertEquals("random@gmail.com", user.getEmail());
        Assertions.assertEquals("Dilan", user.getFirstName());
        Assertions.assertEquals("Valkov", user.getLastName());
        Assertions.assertEquals("0899363324", user.getPhoneNumber());

        Assertions.assertTrue(passwordEncoder.matches("111", user.getPassword()));
    }

    @Test
    @WithMockUser(username = "ivan.valkov@gmail.com")
    void testDeleteAdminUser() throws Exception {
        User testAdmin = userTestData.createTestAdmin("ivan.valkov@gmail.com");
        mockMvc.perform(delete("/user/delete/{id}", 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "ivan.valkov@gmail.com")
    void testDeleteUser() throws Exception {
        User testUser = userTestData.createTestUser("ivan.valkov@gmail.com");
        mockMvc.perform(delete("/user/delete/{id}", 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "ivan.valkov@gmail.com")
    void testUpdateUser() throws Exception {
        User testUser = userTestData.createTestUser("ivan.valkov@gmail.com");
        mockMvc.perform(post("/user/update")
                        .param("firstName", "Petya")
                        .param("lastName", "Penkova")
                        .param("phoneNumber", "0899363326")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));

        Optional<User> optionalUser = userRepository.findByEmail("ivan.valkov@gmail.com");

        Assertions.assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        Assertions.assertEquals("Petya", user.getFirstName());
        Assertions.assertEquals("Penkova", user.getLastName());
        Assertions.assertEquals("0899363326", user.getPhoneNumber());
    }


    @Test
    @WithMockUser(username = "ivan.valkov@gmail.com")
    void testChangeUserPassword() throws Exception {
        User testUser = userTestData.createTestUser("ivan.valkov@gmail.com");

        mockMvc.perform(post("/user/change-password")
                        .param("oldPassword", "111")
                        .param("newPassword", "222")
                        .param("confirmPassword", "222")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));
        Optional<User> optionalUser = userRepository.findByEmail("ivan.valkov@gmail.com");

        Assertions.assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        Assertions.assertTrue(passwordEncoder.matches("222",user.getPassword()));

    }

}
