package com.isep.acme.dto.mapper;

import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.repository.ProductRepository;
import com.isep.acme.dto.message.ReviewMessage;
import com.isep.acme.dto.request.ReviewRequest;
import com.isep.acme.dto.response.ReviewResponse;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReviewMapper {

    private final ProductRepository productRepository;

    public Review toEntity(ReviewRequest reviewRequest){
        
        Review review = new Review();
        review.setUser(reviewRequest.getUser());
        review.setReviewText(reviewRequest.getReviewText());
        review.setRate(reviewRequest.getRating());

        return review;
    }

    public Review toEntity(ReviewMessage reviewMessage){

        Product product = productRepository.findBySku(reviewMessage.getSku()).orElseThrow();
        return new Review(
            reviewMessage.getIdReview(),
            reviewMessage.getApprovalStatus(),
            reviewMessage.getReviewText(),
            reviewMessage.getReport(),
            reviewMessage.getPublishingDate(),
            reviewMessage.getFunFact(),
            product,
            reviewMessage.getUser(),
            reviewMessage.getRate()
        );
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

    public ReviewMessage toMessage(Review review){
        return new ReviewMessage(
            review.getIdReview(),
            review.getApprovalStatus(),
            review.getReviewText(),
            review.getReport(),
            review.getPublishingDate(),
            review.getFunFact(),
            review.getProduct().getSku(),
            review.getUser(),
            review.getRate()
        );
    }
}
