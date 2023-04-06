package com.isep.acme.domain.service;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.enumarate.ApprovalStatus;

public interface ReviewService {

    Review createReviewForProduct(Review review, Product product);

    Review save(Review review);

    void deleteReview(Long reviewId);

    Review moderateReview(Long reviewID, ApprovalStatus approvalStatus);

}
