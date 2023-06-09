package com.isep.acme.domain.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.enumarate.ApprovalStatus;
import com.isep.acme.domain.repository.ReviewRepository;
import com.isep.acme.domain.service.RestService;
import com.isep.acme.domain.service.ReviewService;
import com.isep.acme.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final RestService restService;
    private final ReviewRepository reviewRepository;

    @Override
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
    public void deleteReview(UUID reviewId){
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public Review moderateReview(UUID reviewId, ApprovalStatus approvalStatus){

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Review not found");
        });

        review.setApprovalStatus(approvalStatus);
        return reviewRepository.save(review);
    }
}