package Valkov.Fishing_Farm_Zasmyano.service;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.review.AddReviewDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.review.ReviewDto;

import java.util.List;

public interface ReviewService {
    void createReview(AddReviewDto addReviewDto);

    void deleteAllUserReviews(Long id);

    void deleteReviewById(Long id);


    List<ReviewDto> getAllReviews();

    List<ReviewDto> getAllReviewsByUserId(Long userId);

}
