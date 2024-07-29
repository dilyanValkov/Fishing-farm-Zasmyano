package com.valkov.fishingfarm.service;

import com.valkov.fishingfarm.domain.dto.review.AddReviewDto;
import com.valkov.fishingfarm.domain.dto.review.ReviewDto;

import java.util.List;

public interface ReviewService {
    void createReview(AddReviewDto addReviewDto);

    void deleteAllUserReviews(Long id);

    void deleteReviewById(Long id);


    List<ReviewDto> getAllReviews();

    List<ReviewDto> getAllReviewsByUserId(Long userId);

}
