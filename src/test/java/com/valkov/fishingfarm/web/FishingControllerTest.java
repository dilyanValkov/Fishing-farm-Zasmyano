package com.valkov.fishingfarm.web;

import com.valkov.fishingfarm.domain.enums.FishingSpotNumber;
import com.valkov.fishingfarm.domain.model.FishingReservation;
import com.valkov.fishingfarm.repository.bungalow.BungalowRepository;
import com.valkov.fishingfarm.repository.fishing.FishingBookRepository;
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
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FishingControllerTest {

    public static final String TEST_START_DATE = "2024-08-05";
    public static final String TEST_END_DATE = "2024-08-06";
    public static final String TEST_FISHERMAN_COUNT = "2";
    public static final String TEST_FISHING_HOURS = "DAY";
    public static final FishingSpotNumber TEST_FISHING_SPOT = FishingSpotNumber.ONE;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserTestData userTestData;

    @Autowired
    private FishingBookRepository fishingBookRepository;

    @Autowired
    private DBTestData dbTestData;

    @BeforeEach
    void setUp() {
        userTestData.createTestUser("petko@gmail.com", "0899363327");
    }

    @AfterEach
    void tearDown() {
        dbTestData.cleanUp();
        userTestData.cleanUp();
    }

    @Test
    @WithMockUser("petko@gmail.com")
    public void testViewFishing() throws Exception {
        mockMvc.perform(get("/book-fishing"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-fishing"))
                .andExpect(model().attributeExists("fishingSpots"))
                .andExpect(model().attributeExists("today"));
    }

    @Test
    @WithMockUser("petko@gmail.com")
    void testDoReservation() throws Exception {
        mockMvc.perform(post("/book-fishing")
                        .param("startDate", TEST_START_DATE)
                        .param("endDate", TEST_END_DATE)
                        .param("fishermanCount", TEST_FISHERMAN_COUNT)
                        .param("fishingHours", TEST_FISHING_HOURS)
                        .param("fishingSpot", TEST_FISHING_SPOT.name())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book-info"));
        FishingReservation saved = fishingBookRepository.findAllByEmail("petko@gmail.com").getFirst();
        Assertions.assertEquals(TEST_START_DATE, saved.getStartDate().toString());
        Assertions.assertEquals(TEST_END_DATE, saved.getEndDate().toString());
        Assertions.assertEquals(TEST_FISHERMAN_COUNT, saved.getFishermanCount().toString());
        Assertions.assertEquals(TEST_FISHING_HOURS, saved.getFishingHours().toString());
        Assertions.assertEquals(TEST_FISHING_SPOT.getNumber(), saved.getFishingSpot().getId());
    }
}
