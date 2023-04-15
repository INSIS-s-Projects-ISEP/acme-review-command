package com.isep.acme.api.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.enumarate.ApprovalStatus;
import com.isep.acme.domain.service.ProductService;
import com.isep.acme.domain.service.ReviewService;
import com.isep.acme.dto.mapper.ReviewMapper;
import com.isep.acme.dto.request.ReviewRequest;
import com.isep.acme.dto.response.ReviewResponse;
import com.isep.acme.exception.ResourceNotFoundException;
import com.isep.acme.messaging.ReviewProducer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Tag(name = "Review", description = "Endpoints for managing Review")
@Slf4j
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
            throw new ResourceNotFoundException(sku);
        });

        reviewService.createReviewForProduct(review, product);
        reviewProducer.reviewCreated(review);
        log.info("Review created: " + review.getReviewId());
        
        ReviewResponse reviewResponse = reviewMapper.toResponse(review);
        return new ResponseEntity<ReviewResponse>(reviewResponse, HttpStatus.CREATED);
    }
    
    @Operation(summary = "deletes review")
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable(value = "reviewId") UUID reviewId) {
        reviewService.deleteReview(reviewId);
        reviewProducer.reviewDeleted(reviewId);
        log.info("Review deleted: " + reviewId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Accept or reject review")
    @PatchMapping("/reviews/{reviewId}/acceptreject/{approvalStatus}")
    public ResponseEntity<ReviewResponse> putAcceptRejectReview(@PathVariable UUID reviewId, @PathVariable ApprovalStatus approvalStatus){

        try {
            Review review = reviewService.moderateReview(reviewId, approvalStatus);
            reviewProducer.reviewUpdated(review);
            log.info("Review updated: " + reviewId + " Approval Status: " + approvalStatus);
            
            ReviewResponse reviewResponse = reviewMapper.toResponse(review);
            return ResponseEntity.ok().body(reviewResponse);
        }
        catch( ResourceNotFoundException e ) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
