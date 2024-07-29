package com.valkov.fishingfarm.service.book;

import com.valkov.fishingfarm.domain.dto.bungalow.BookBungalowDto;
import com.valkov.fishingfarm.domain.dto.bungalow.BookInfoBungalowDto;
import com.valkov.fishingfarm.domain.model.Bungalow;
import jakarta.mail.MessagingException;

import java.util.List;

public interface BungalowBookService {
    boolean book (BookBungalowDto dto) throws MessagingException;
    List<Bungalow> allBungalows();
    List<BookInfoBungalowDto> getAllUserBookings();
    void deleteReservations(Long userId);
    List<BookInfoBungalowDto> getAllBookings();
}
