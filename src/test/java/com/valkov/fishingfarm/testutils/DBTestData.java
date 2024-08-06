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

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DBTestData {

    @Autowired
    private BungalowBookRepository bungalowBookRepository;

    @Autowired
    private FishingBookRepository fishingBookRepository;

    @Autowired
    private FishingSpotRepository fishingSpotRepository;

    @Autowired
    private BungalowRepository bungalowRepository;

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
        fishingReservation.setId(1L);
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
      // bungalowReservation.setId(1L);
        return bungalowBookRepository.save(bungalowReservation);
    }

    public void cleanUp() {
        bungalowBookRepository.deleteAll();
        fishingBookRepository.deleteAll();
        bungalowRepository.deleteAll();
        fishingSpotRepository.deleteAll();
    }

    public FishingSpot fishingSpot() {
        FishingSpot fishingSpot = new FishingSpot();
        fishingSpot.setCapacity(2);
        fishingSpot.setDayPrice(BigDecimal.valueOf(25));
        fishingSpot.setDayAndNightPrice(BigDecimal.valueOf(40));
        fishingSpot.setId(1L);
        return fishingSpotRepository.save(fishingSpot);
    }

    public Bungalow bungalow() {
        Bungalow bungalow = new Bungalow();
        bungalow.setCapacity(2);
        bungalow.setPrice(BigDecimal.valueOf(80));
        //bungalow.setId(1L);
        return bungalowRepository.save(bungalow);
    }

    public BungalowReservation getBungalowReservationById(Long id){
        return bungalowBookRepository.getReferenceById(id);
    }
    public FishingReservation getFishingReservationById(Long id){
        return fishingBookRepository.getReferenceById(id);
    }
}


