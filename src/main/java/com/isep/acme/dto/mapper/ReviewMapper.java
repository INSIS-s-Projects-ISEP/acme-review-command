package com.isep.acme.dto.mapper;

import org.springframework.stereotype.Component;

import com.isep.acme.api.controllers.ResourceNotFoundException;
import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.User;
import com.isep.acme.domain.service.UserService;
import com.isep.acme.dto.request.ReviewRequest;
import com.isep.acme.dto.response.ReviewResponse;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReviewMapper {

    private final UserService userService;

    public Review toEntity(ReviewRequest reviewRequest){
        
        Review review = new Review();

        User user = userService.getUserId(reviewRequest.getUserID()).orElseThrow(() -> {
            throw new ResourceNotFoundException(User.class, reviewRequest.getUserID());
        });

        review.setUser(user);
        review.setReviewText(reviewRequest.getReviewText());
        review.setRate(reviewRequest.getRating());

        return review;
    }

    public ReviewResponse toResponse(Review review){
        return new ReviewResponse(
            review.getIdReview(),
            review.getReviewText(), 
            review.getPublishingDate(), 
            review.getApprovalStatus(), 
            review.getFunFact(), 
            review.getRate()
        );
    }
}
