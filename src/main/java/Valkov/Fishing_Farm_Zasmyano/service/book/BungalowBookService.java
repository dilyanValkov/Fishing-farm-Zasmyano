package Valkov.Fishing_Farm_Zasmyano.service.book;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.bungalow.BookBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.bungalow.BookInfoBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.model.Bungalow;
import jakarta.mail.MessagingException;

import java.util.List;

public interface BungalowBookService {
    boolean book (BookBungalowDto dto) throws MessagingException;
    List<Bungalow> allBungalows();
    List<BookInfoBungalowDto> getAllBookings();
}
