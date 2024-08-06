package com.valkov.fishingfarm.web;

import com.valkov.fishingfarm.domain.model.Bungalow;
import com.valkov.fishingfarm.domain.model.BungalowReservation;
import com.valkov.fishingfarm.domain.model.FishingReservation;
import com.valkov.fishingfarm.domain.model.FishingSpot;
import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.repository.bungalow.BungalowBookRepository;
import com.valkov.fishingfarm.service.impl.EmailService;
import com.valkov.fishingfarm.testutils.DBTestData;
import com.valkov.fishingfarm.testutils.UserTestData;
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

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class BungalowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserTestData userTestData;

    @Autowired
    private BungalowBookRepository bungalowBookRepository;

    @Autowired
    private DBTestData dbTestData;

    @MockBean
    private EmailService emailService;

    @BeforeEach
    void setUp(){
        userTestData.cleanUp();
        dbTestData.cleanUp();
    }

    @AfterEach
    void tearDown(){
        userTestData.cleanUp();
        dbTestData.cleanUp();
    }

    @Test
    @WithMockUser("petko@gmail.com")
    public void testViewBungalow() throws Exception {
        mockMvc.perform(get("/book-bungalow"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-bungalow"))
                .andExpect(model().attributeExists("bungalows"))
                .andExpect(model().attributeExists("today"))
                .andExpect(model().attributeExists("tomorrow"));
    }


    @Test
    @WithMockUser("petko@gmail.com")
    void testDoReservation() throws Exception {
        Bungalow bungalow = dbTestData.bungalow();
        User testUser = userTestData.createTestUser("petko@gmail.com");
        BungalowReservation bungalowReservation = dbTestData.createBungalowReservation(testUser, bungalow);

        mockMvc.perform(post("/book-bungalow")
                        .param("startDate", "2024-08-05")
                        .param("endDate", "2024-08-06")
                        .param("number", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book-info"));
        List<BungalowReservation> saved = bungalowBookRepository.findAllByEmail("petko@gmail.com");

        Assertions.assertEquals("2024-08-05",saved.getFirst().getStartDate().toString());
        Assertions.assertEquals("2024-08-06",saved.getFirst().getEndDate().toString());
        Assertions.assertEquals(1L,saved.getFirst().getBungalow().getId());
    }
    
}
