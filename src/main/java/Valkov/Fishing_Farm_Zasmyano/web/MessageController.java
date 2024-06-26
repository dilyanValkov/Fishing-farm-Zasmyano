package Valkov.Fishing_Farm_Zasmyano.web;

import Valkov.Fishing_Farm_Zasmyano.config.UserSession;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookInfoBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.service.BungalowBookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MessageController {

    private final UserSession userSession;
    private final BungalowBookingService bungalowBookingService;

    public MessageController(UserSession userSession, BungalowBookingService bungalowBookingService) {
        this.userSession = userSession;
        this.bungalowBookingService = bungalowBookingService;
    }

    @GetMapping("/book-info")
    public String viewMessage(Model model){
        if (!userSession.isUserLoggedIn()){
            return "redirect:/";
        }

        BookInfoBungalowDto dto = bungalowBookingService.getBookInfoBungalowDto();
        model.addAttribute("bookInfo", dto);
        return "book-info";
    }
}
