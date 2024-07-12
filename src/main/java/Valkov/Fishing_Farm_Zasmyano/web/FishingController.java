package Valkov.Fishing_Farm_Zasmyano.web;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.fishing.BookFishingDto;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.FishingHours;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.FishingSpotNumber;
import Valkov.Fishing_Farm_Zasmyano.domain.model.FishingSpot;
import Valkov.Fishing_Farm_Zasmyano.repository.fishing.FishingSpotRepository;
import Valkov.Fishing_Farm_Zasmyano.service.book.FishingBookService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
@Transactional
@Controller
@RequiredArgsConstructor
public class FishingController {
    private final FishingBookService fishingBookService;
    private final FishingSpotRepository fishingSpotRepository;

    @ModelAttribute(name = "bookingData")
    public BookFishingDto bookFishingDto(){
        return new BookFishingDto();
    }
    @ModelAttribute(name = "allFishingSpots")
    public FishingSpotNumber[] fishingSpotNumbers(){
        return FishingSpotNumber.values();
    }

    @ModelAttribute(name = "allFishingHours")
    public FishingHours [] fishingHours(){
        return FishingHours.values();
    }
    @GetMapping("/book-fishing")
    public String viewFishing(Model model){
        model.addAttribute("today", LocalDate.now());
        return "book-fishing";
    }

    @PostMapping("/book-fishing")
    public String doReservation(@Valid BookFishingDto dto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {


       FishingSpot fishingSpot = fishingSpotRepository.findById(dto.getFishingSpot().getNumber()).get();

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            redirectAttributes.addFlashAttribute("dateError", dto);
            return "redirect:/book-fishing";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("bookingData", dto);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.bookingData", bindingResult);
            return "redirect:/book-fishing";
        }
        if (dto.getFishermanCount() > fishingSpot.getCapacity()){
            redirectAttributes.addFlashAttribute("countError",dto);
            return "redirect:/book-fishing";
        }
        boolean book = fishingBookService.book(dto);

        if (!book) {
            redirectAttributes.addFlashAttribute("bookStatus", dto);
            return "redirect:/book-fishing";
        }

        return "redirect:/book-info";
    }


}
