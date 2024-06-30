package Valkov.Fishing_Farm_Zasmyano.web;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookInfoBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.service.BungalowBookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MessageController {

    private final BungalowBookingService bungalowBookingService;

    public MessageController(BungalowBookingService bungalowBookingService) {
        this.bungalowBookingService = bungalowBookingService;

    }

    @GetMapping("/book-info")
    public String viewMessage(Model model){
        BookInfoBungalowDto dto = bungalowBookingService.getBookInfoBungalowDto();
        model.addAttribute("bookInfo", dto);
        return "book-info";
    }
}
