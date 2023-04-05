package com.isep.acme.domain.service.impl;

import org.springframework.stereotype.Service;

import com.isep.acme.api.controllers.ResourceNotFoundException;
import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.repository.ReviewRepository;
import com.isep.acme.domain.service.RestService;
import com.isep.acme.domain.service.ReviewService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final RestService restService;
    private final ReviewRepository reviewRepository;

    public Review createReviewForProduct(Review review, Product product){
        String funfact = restService.getFunFact(review.getPublishingDate());
        
        review.setProduct(product);
        review.setFunFact(funfact);
        return save(review);
    }

    @Override
    public Review save(Review review){
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId){
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public Review moderateReview(Long reviewID, String approved){

        Review review = reviewRepository.findById(reviewID).orElseThrow(() -> {
            throw new ResourceNotFoundException("Review not found");
        });

        review.setApprovalStatus(approved);
        return reviewRepository.save(review);
    }
}