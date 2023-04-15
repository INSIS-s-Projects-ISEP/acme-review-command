package com.isep.acme.domain.service;

import java.util.UUID;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.enumarate.ApprovalStatus;

public interface ReviewService {

    Review createReviewForProduct(Review review, Product product);

    Review save(Review review);

    void deleteReview(UUID reviewId);

    Review moderateReview(UUID reviewId, ApprovalStatus approvalStatus);

}
