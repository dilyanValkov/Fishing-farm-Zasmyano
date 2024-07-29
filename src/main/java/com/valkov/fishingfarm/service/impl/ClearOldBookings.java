package com.valkov.fishingfarm.service.impl;

import com.valkov.fishingfarm.domain.enums.Status;
import com.valkov.fishingfarm.repository.bungalow.BungalowBookRepository;
import com.valkov.fishingfarm.repository.fishing.FishingBookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClearOldBookings {

    private final FishingBookRepository fishingBookRepository;
    private final BungalowBookRepository bungalowBookRepository;

    @Transactional
    @Scheduled(cron = "@monthly")
    public void deleteRejectedBookings(){
    fishingBookRepository.deleteAllByStatus(Status.REJECTED);
    bungalowBookRepository.deleteAllByStatus(Status.REJECTED);
    }
}
