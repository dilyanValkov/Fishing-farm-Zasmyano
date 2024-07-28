package Valkov.Fishing_Farm_Zasmyano.service.impl;

import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import Valkov.Fishing_Farm_Zasmyano.repository.bungalow.BungalowBookRepository;
import Valkov.Fishing_Farm_Zasmyano.repository.fishing.FishingBookRepository;
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
