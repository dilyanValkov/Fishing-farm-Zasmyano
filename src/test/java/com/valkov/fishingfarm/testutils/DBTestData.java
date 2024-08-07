package com.valkov.fishingfarm.testutils;

import com.valkov.fishingfarm.domain.enums.FishingHours;
import com.valkov.fishingfarm.domain.enums.Status;
import com.valkov.fishingfarm.domain.model.Bungalow;
import com.valkov.fishingfarm.domain.model.BungalowReservation;
import com.valkov.fishingfarm.domain.model.FishingReservation;
import com.valkov.fishingfarm.domain.model.FishingSpot;
import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.repository.bungalow.BungalowBookRepository;
import com.valkov.fishingfarm.repository.bungalow.BungalowRepository;
import com.valkov.fishingfarm.repository.fishing.FishingBookRepository;
import com.valkov.fishingfarm.repository.fishing.FishingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DBTestData {

    public static final long TEST_FISHING_SPOT_ID = 1L;
    public static final long TEST_BUNGALOW_ID = 1L;

    @Autowired
    private BungalowRepository bungalowRepository;

    @Autowired
    private BungalowBookRepository bungalowBookRepository;

    @Autowired
    private FishingSpotRepository fishingSpotRepository;

    @Autowired
    private FishingBookRepository fishingBookRepository;


    public FishingReservation createFishingReservation(User user, FishingSpot fishingSpot) {
        LocalDate startDate = LocalDate.of(2024, 8, 3);
        LocalDate endDate = LocalDate.of(2024, 8, 4);
        FishingReservation fishingReservation = new FishingReservation();
        fishingReservation.setStartDate(startDate);
        fishingReservation.setEndDate(endDate);
        fishingReservation.setUser(user);
        fishingReservation.setFishingSpot(fishingSpot);
        fishingReservation.setFishermanCount(2);
        fishingReservation.setFishingHours(FishingHours.DAY);
        return fishingBookRepository.save(fishingReservation);
    }

    public BungalowReservation createBungalowReservation(User user, Bungalow bungalow) {
        LocalDate startDate = LocalDate.of(2024, 8, 3);
        LocalDate endDate = LocalDate.of(2024, 8, 4);
        BungalowReservation bungalowReservation = new BungalowReservation();
        bungalowReservation.setBungalow(bungalow);
        bungalowReservation.setStatus(Status.UNCONFIRMED);
        bungalowReservation.setUser(user);
        bungalowReservation.setStartDate(startDate);
        bungalowReservation.setEndDate(endDate);
        return bungalowBookRepository.save(bungalowReservation);
    }

    public void cleanUp() {
        bungalowBookRepository.deleteAll();
        fishingBookRepository.deleteAll();
    }


    public BungalowReservation getBungalowReservationById(Long id) {
        return bungalowBookRepository.getReferenceById(id);
    }

    public FishingReservation getFishingReservationById(Long id) {
        return fishingBookRepository.getReferenceById(id);
    }

    public Bungalow getBungalowById(Long id) {
        return bungalowRepository.getReferenceById(id);
    }

    public FishingSpot getFishingSpotById(Long id) {
        return fishingSpotRepository.getReferenceById(id);
    }
}


