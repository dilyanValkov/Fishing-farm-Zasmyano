package com.valkov.fishingfarm.web;

import com.valkov.fishingfarm.domain.model.Bungalow;
import com.valkov.fishingfarm.domain.model.BungalowReservation;
import com.valkov.fishingfarm.repository.bungalow.BungalowBookRepository;
import com.valkov.fishingfarm.testutils.DBTestData;
import com.valkov.fishingfarm.testutils.UserTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.valkov.fishingfarm.testutils.DBTestData.TEST_BUNGALOW_ID;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BungalowControllerTest {

    public static final String TEST_START_DATE = "2024-08-05";
    public static final String TEST_END_DATE = "2024-08-06";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserTestData userTestData;

    @Autowired
    private BungalowBookRepository bungalowBookRepository;

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
        Bungalow bungalow = dbTestData.getBungalowById(TEST_BUNGALOW_ID);
        mockMvc.perform(post("/book-bungalow")
                        .param("startDate", TEST_START_DATE)
                        .param("endDate", TEST_END_DATE)
                        .param("number", String.valueOf(bungalow.getId()))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book-info"));
        BungalowReservation saved = bungalowBookRepository.findAllByEmail("petko@gmail.com").getFirst();

        Assertions.assertEquals(TEST_START_DATE, saved.getStartDate().toString());
        Assertions.assertEquals(TEST_END_DATE, saved.getEndDate().toString());
    }

}
