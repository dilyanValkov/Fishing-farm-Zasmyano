package com.valkov.fishingfarm.web;
import com.valkov.fishingfarm.domain.dto.bungalow.BookInfoBungalowDto;
import com.valkov.fishingfarm.domain.dto.fishing.BookInfoFishingDto;
import com.valkov.fishingfarm.service.book.BungalowBookService;
import com.valkov.fishingfarm.service.book.FishingBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingInfoController {

    private final BungalowBookService bungalowBookingService;
    private final FishingBookService fishingBookService;


    @GetMapping("/book-info")
    public String viewBooking(Model model){
        List<BookInfoBungalowDto> allBungalowBookings = bungalowBookingService.getAllUserBookings();
        List<BookInfoFishingDto> allFishingBookings = fishingBookService.getAllUserBookings();

        model.addAttribute("allBungalowBookings",allBungalowBookings);
        model.addAttribute("allFishingBookings",allFishingBookings);

        return "book-info";
    }


    
}
