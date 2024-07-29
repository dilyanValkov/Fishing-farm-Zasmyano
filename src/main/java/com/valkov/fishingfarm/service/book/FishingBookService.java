package com.valkov.fishingfarm.service.book;
import com.valkov.fishingfarm.domain.dto.fishing.BookFishingDto;
import com.valkov.fishingfarm.domain.dto.fishing.BookInfoFishingDto;
import java.util.List;

public interface FishingBookService {

    boolean book(BookFishingDto dto);

    List<BookInfoFishingDto> getAllUserBookings();

    void deleteReservations(Long userId);

    boolean isFishingSpotHasCapacity(BookFishingDto dto);

    List<BookInfoFishingDto> getAllBookings();
}
