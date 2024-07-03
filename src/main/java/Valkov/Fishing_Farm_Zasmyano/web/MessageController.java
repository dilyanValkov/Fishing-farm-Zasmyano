package Valkov.Fishing_Farm_Zasmyano.web;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookInfoBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.service.BungalowBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final BungalowBookService bungalowBookingService;


    @GetMapping("/book-info")
    public String viewMessage(Model model){
        BookInfoBungalowDto dto = bungalowBookingService.getBookInfoBungalowDto();
        model.addAttribute("bookInfo", dto);
        return "book-info";
    }
}
