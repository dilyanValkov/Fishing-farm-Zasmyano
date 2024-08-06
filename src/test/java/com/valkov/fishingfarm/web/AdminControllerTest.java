package com.valkov.fishingfarm.web;

import com.valkov.fishingfarm.domain.model.Bungalow;
import com.valkov.fishingfarm.domain.model.BungalowReservation;
import com.valkov.fishingfarm.domain.model.FishingReservation;
import com.valkov.fishingfarm.domain.model.FishingSpot;
import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.repository.user.UserRepository;
import com.valkov.fishingfarm.service.impl.EmailService;
import com.valkov.fishingfarm.testutils.DBTestData;
import com.valkov.fishingfarm.testutils.UserTestData;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserTestData userTestData;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DBTestData dbTestData;

    @MockBean
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        User testAdmin = userTestData.createTestAdmin("ivan.valkov@gmail.com");
    }

    @AfterEach
    void tearDown(){
        dbTestData.cleanUp();
    }


    @Test
    @WithMockUser(username = "ivan.valkov@gmail.com", roles = {"ADMIN", "USER"})
   public void testViewAdminUserPage() throws Exception {

        mockMvc.perform(get("/admin/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-user"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithMockUser(username = "ivan.valkov@gmail.com", roles = {"ADMIN", "USER"})
    public void testViewAdminBungalowPage() throws Exception {
        mockMvc.perform(get("/admin/book/bungalow"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-book-bungalow"))
                .andExpect(model().attributeExists("bungalowBookings"));
    }

    @Test
    @WithMockUser(username = "ivan.valkov@gmail.com", roles = {"ADMIN", "USER"})
    public void testViewAdminFishingPage() throws Exception {
        mockMvc.perform(get("/admin/book/fishing"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-book-fishing"))
                .andExpect(model().attributeExists("fishingBookings"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "ivan.valkov@gmail.com", roles = {"ADMIN", "USER"})
    public void testUpdateBookBungalowStatus() throws Exception {
        Optional<User> byEmail = userRepository.findByEmail("ivan.valkov@gmail.com");
        Assertions.assertTrue(byEmail.isPresent());
        Bungalow bungalow = dbTestData.bungalow();
        User user = byEmail.get();

        BungalowReservation bungalowReservation = dbTestData.createBungalowReservation(user, bungalow);

        mockMvc.perform(post("/admin/book/bungalow/updateStatus")
                        .param("status", "CONFIRMED")
                        .param("reservationNumber", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/book/bungalow"));

        BungalowReservation saved = dbTestData.getBungalowReservationById(bungalowReservation.getId());
        Assertions.assertEquals("CONFIRMED", saved.getStatus().toString());
    }

    @Test
    @Transactional
    @WithMockUser(username = "ivan.valkov@gmail.com", roles = {"ADMIN", "USER"})
    public void testUpdateBookFishingStatus() throws Exception {
        FishingSpot fishingSpot = dbTestData.fishingSpot();

        Optional<User> byEmail = userRepository.findByEmail("ivan.valkov@gmail.com");
        Assertions.assertTrue(byEmail.isPresent());
        User user = byEmail.get();

        FishingReservation fishingReservation = dbTestData.createFishingReservation(user, fishingSpot);
        mockMvc.perform(post("/admin/book/fishing/updateStatus")
                        .param("status", "CONFIRMED")
                        .param("reservationNumber", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/book/fishing"));

        FishingReservation saved = dbTestData.getFishingReservationById(fishingReservation.getId());
        Assertions.assertEquals("CONFIRMED", saved.getStatus().toString());
    }
}
