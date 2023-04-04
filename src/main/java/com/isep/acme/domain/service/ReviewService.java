package com.isep.acme.domain.service;

import java.util.List;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.model.Review;
import com.isep.acme.dto.ReviewResponse;

public interface ReviewService {

    Iterable<Review> getAll();

    List<ReviewResponse> getReviewsOfProduct(String sku, String status);

    Review createReviewForProduct(Review review, Product product);

    Review create(Review review);

    Double getWeightedAverage(Product product);

    Boolean DeleteReview(Long reviewId);

    List<ReviewResponse> findPendingReview();

    ReviewResponse moderateReview(Long reviewID, String approved);

    List<ReviewResponse> findReviewsByUser(Long userID);
}
