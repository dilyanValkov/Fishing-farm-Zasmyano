package Valkov.Fishing_Farm_Zasmyano.service;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.model.Bungalow;

import java.util.List;

public interface BungalowBookingService {
    String book (BookBungalowDto dto);
    List<Bungalow> allBungalows();
}
