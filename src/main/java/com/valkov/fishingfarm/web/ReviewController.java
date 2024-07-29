package com.valkov.fishingfarm.web;

import com.valkov.fishingfarm.domain.dto.review.AddReviewDto;
import com.valkov.fishingfarm.domain.dto.review.ReviewDto;
import com.valkov.fishingfarm.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    @ModelAttribute(name = "addReviewDto")
    public AddReviewDto addReviewDto(){
        return new AddReviewDto();
    }

    @ModelAttribute(name = "reviewDto")
    public ReviewDto reviewDto(){
        return new ReviewDto();
    }

    @GetMapping()
    public String viewReview(){
        return "review";
    }

    @PostMapping("/add")
    public String addReview(@Valid AddReviewDto dto,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addReviewDto", dto);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.addReviewDto", bindingResult);
            return "redirect:/review";
        }

        reviewService.createReview(dto);
        return "redirect:/review/all-review";
    }

    @GetMapping("/all-review")
    public String viewAllReviews(Model model, @RequestParam(defaultValue = "0") int page){
        int pageSize = 5;

        List<ReviewDto> allReviews = reviewService.getAllReviews().reversed();

        int totalPages = (int) Math.ceil((double) allReviews.size() / pageSize);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", pageSize);

        model.addAttribute("allReviews", allReviews);
        return "all-review";
    }


    @DeleteMapping("/delete/{id}")
    public String deleteReviewById(@PathVariable Long id) {

        reviewService.deleteReviewById(id);
        return "redirect:/review/all-review";
    }
}
