package com.valkov.fishingfarm.web;

import com.valkov.fishingfarm.domain.dto.fishing.BookFishingDto;
import com.valkov.fishingfarm.domain.enums.FishingHours;
import com.valkov.fishingfarm.domain.enums.FishingSpotNumber;
import com.valkov.fishingfarm.domain.model.FishingSpot;
import com.valkov.fishingfarm.repository.fishing.FishingSpotRepository;
import com.valkov.fishingfarm.service.book.FishingBookService;
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
import java.util.List;

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
        List<FishingSpot> fishingSpots = fishingSpotRepository.findAll();

        model.addAttribute("fishingSpots",fishingSpots);
        model.addAttribute("today", LocalDate.now());
        return "book-fishing";
    }

    @PostMapping("/book-fishing")
    public String doReservation(@Valid BookFishingDto dto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

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

        if (fishingBookService.isFishingSpotHasCapacity(dto)){
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
