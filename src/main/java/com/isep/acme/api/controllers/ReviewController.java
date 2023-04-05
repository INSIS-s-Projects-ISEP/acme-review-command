package com.isep.acme.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.service.ProductService;
import com.isep.acme.domain.service.ReviewService;
import com.isep.acme.dto.mapper.ReviewMapper;
import com.isep.acme.dto.request.ReviewRequest;
import com.isep.acme.dto.response.ReviewResponse;
import com.isep.acme.messaging.ReviewProducer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;


@Tag(name = "Review", description = "Endpoints for managing Review")
@RestController
@AllArgsConstructor
class ReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;

    private final ReviewProducer reviewProducer;
    private final ReviewMapper reviewMapper;

    @Operation(summary = "creates review")
    @PostMapping("/products/{sku}/reviews")
    public ResponseEntity<ReviewResponse> createReview(@PathVariable(value = "sku") String sku, @RequestBody ReviewRequest reviewRequest) {

        Review review = reviewMapper.toEntity(reviewRequest);
        Product product = productService.findBySku(sku).orElseThrow(() -> {
            throw new ResourceNotFoundException(Product.class, reviewRequest.getUserID());
        });

        reviewService.createReviewForProduct(review, product);
        reviewProducer.reviewCreated(review);
        
        ReviewResponse reviewResponse = reviewMapper.toResponse(review);
        return new ResponseEntity<ReviewResponse>(reviewResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "deletes review")
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable(value = "reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Accept or reject review")
    @PutMapping("/reviews/acceptreject/{reviewId}")
    public ResponseEntity<ReviewResponse> putAcceptRejectReview(@PathVariable(value = "reviewId") Long reviewId, @RequestBody String approved){

        try {
            Review review = reviewService.moderateReview(reviewId, approved);
            ReviewResponse reviewResponse = reviewMapper.toResponse(review);
            return ResponseEntity.ok().body(reviewResponse);
        }
        catch( ResourceNotFoundException e ) {
            return ResponseEntity.notFound().build();
        }
    }
}
