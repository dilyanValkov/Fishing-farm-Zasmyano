package Valkov.Fishing_Farm_Zasmyano.service.book;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookFishingDto;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookInfoFishingDto;

public interface FishingBookService {

    boolean book(BookFishingDto dto);

    BookInfoFishingDto getBookInfoFishingDto();
}
