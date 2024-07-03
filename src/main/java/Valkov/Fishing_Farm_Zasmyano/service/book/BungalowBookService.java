package Valkov.Fishing_Farm_Zasmyano.service.book;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookInfoBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.model.Bungalow;
import java.util.List;

public interface BungalowBookService {
    boolean book (BookBungalowDto dto);
    List<Bungalow> allBungalows();

    BookInfoBungalowDto getBookInfoBungalowDto();
}
