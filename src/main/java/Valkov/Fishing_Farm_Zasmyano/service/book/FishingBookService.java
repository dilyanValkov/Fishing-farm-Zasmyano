package Valkov.Fishing_Farm_Zasmyano.service.book;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.fishing.BookFishingDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.fishing.BookInfoFishingDto;
import java.util.List;

public interface FishingBookService {

    boolean book(BookFishingDto dto);

    List<BookInfoFishingDto> getAllBookings();
    void deleteReservations(Long userId);
}
