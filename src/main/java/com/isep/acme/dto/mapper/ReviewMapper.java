package com.isep.acme.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.isep.acme.api.controllers.ResourceNotFoundException;
import com.isep.acme.domain.model.Rating;
import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.User;
import com.isep.acme.domain.service.RatingService;
import com.isep.acme.domain.service.UserService;
import com.isep.acme.dto.ReviewResponse;
import com.isep.acme.dto.request.ReviewRequest;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReviewMapper {

    private final UserService userService;
    private final RatingService ratingService;

    public Review toEntity(ReviewRequest reviewRequest){
        
        Review review = new Review();

        Optional<User> optUser = userService.getUserId(reviewRequest.getUserID());
        if(optUser.isEmpty()){
            throw new ResourceNotFoundException(User.class, reviewRequest.getUserID());
        }
        
        Optional<Rating> optRating = ratingService.findByRate(reviewRequest.getRating());
        if(optRating.isEmpty()){
            throw new ResourceNotFoundException(Rating.class, reviewRequest.getUserID());
        }

        review.setUser(optUser.get());
        review.setReviewText(reviewRequest.getReviewText());
        review.setRating(optRating.get());

        return review;
    }

    public ReviewResponse toResponse(Review review){
        return new ReviewResponse(
            review.getIdReview(),
            review.getReviewText(), 
            review.getPublishingDate(), 
            review.getApprovalStatus(), 
            review.getFunFact(), 
            review.getRating().getRate()
        );
    }

    public List<ReviewResponse> toResponseList(List<Review> review) {

        List<ReviewResponse> dtoList = new ArrayList<>();
        for (Review rev: review) {
            dtoList.add(toResponse(rev));
        }
        
        return dtoList;
    }
}
