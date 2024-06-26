package Valkov.Fishing_Farm_Zasmyano.web;

import Valkov.Fishing_Farm_Zasmyano.config.UserSession;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.BookBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.model.Bungalow;
import Valkov.Fishing_Farm_Zasmyano.service.BungalowBookingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BungalowController {
    private final UserSession userSession;
    private final BungalowBookingService bungalowBookingService;

    public BungalowController(UserSession userSession, BungalowBookingService bungalowBookingService) {
        this.userSession = userSession;
        this.bungalowBookingService = bungalowBookingService;
    }

    @ModelAttribute(name = "bookingData")
    public BookBungalowDto bookBungalowDto(){
        return new BookBungalowDto();
    }
    @GetMapping("/book-bungalow")
    public String viewBungalow(Model model){
        List<Bungalow> bungalows = bungalowBookingService.allBungalows();


        model.addAttribute("today", LocalDate.now());
        model.addAttribute("tomorrow", LocalDate.now().plusDays(1));

        model.addAttribute("bungalows", bungalows);
        return "book-bungalow";
    }

    @PostMapping("/book-bungalow")
    public String doReservation(@Valid BookBungalowDto dto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model){

        if (dto.getEndDate().isBefore(dto.getStartDate())||
                dto.getEndDate().isEqual(dto.getStartDate())){
            redirectAttributes.addFlashAttribute("dateError", dto);
            return "redirect:/book-bungalow";
        }
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("bookingData", dto);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.bookingData", bindingResult);
            return "redirect:/book-bungalow";
        }
        boolean book = bungalowBookingService.book(dto);

        if (!book){
            redirectAttributes.addFlashAttribute("bookStatus", dto);
            return "redirect:/book-bungalow";
        }

        return "redirect:/book-info";

    }
}
