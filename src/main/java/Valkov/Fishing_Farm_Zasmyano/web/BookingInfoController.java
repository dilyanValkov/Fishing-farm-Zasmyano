package Valkov.Fishing_Farm_Zasmyano.web;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.bungalow.BookInfoBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.fishing.BookInfoFishingDto;
import Valkov.Fishing_Farm_Zasmyano.service.book.BungalowBookService;
import Valkov.Fishing_Farm_Zasmyano.service.book.FishingBookService;
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
