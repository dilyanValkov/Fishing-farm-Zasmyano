package com.valkov.fishingfarm.service.impl;

import com.valkov.fishingfarm.domain.dto.review.AddReviewDto;
import com.valkov.fishingfarm.domain.dto.review.ReviewDto;
import com.valkov.fishingfarm.service.ReviewService;
import com.valkov.fishingfarm.service.user.UserService;
import com.valkov.fishingfarm.service.user.UserUtilService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final RestClient restClient;
    private final ModelMapper modelMapper;
    private final UserUtilService userUtilService;
    private final UserService userService;

    @Override
    public void createReview(AddReviewDto addReviewDto) {
        Long userId = userUtilService.getCurrentUser().getId();
        ReviewDto reviewDto = modelMapper.map(addReviewDto, ReviewDto.class);
        reviewDto.setUser(userId);

        restClient.post()
                .uri("http://localhost:8081/review/add")
                .body(reviewDto)
                .retrieve();
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        List<ReviewDto> reviews = restClient.get()
                .uri("http://localhost:8081/review")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        if (reviews != null) {
            for (ReviewDto review : reviews) {
                review.setFullName(userService.userFullName(review.getUser()));
                review.setEmail(userService.getUserEmail(review.getUser()));
            }
        }

        return reviews;
    }

    @Override
    public List<ReviewDto> getAllReviewsByUserId(Long userId) {
        return restClient.get()
                .uri("http://localhost:8081/review/user{id}", userId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public void deleteAllUserReviews(Long id) {
        restClient.delete()
                .uri("http://localhost:8081/review/delete/user/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void deleteReviewById(Long id) {
        restClient.delete()
                .uri("http://localhost:8081/review/delete/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

}
