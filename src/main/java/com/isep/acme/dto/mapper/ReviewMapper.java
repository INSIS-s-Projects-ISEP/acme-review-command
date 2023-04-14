package com.isep.acme.dto.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.repository.ProductRepository;
import com.isep.acme.dto.message.ProductMessage;
import com.isep.acme.dto.message.ReviewMessage;
import com.isep.acme.dto.request.ReviewForTempVoteRequest;
import com.isep.acme.dto.request.ReviewRequest;
import com.isep.acme.dto.response.ReviewResponse;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReviewMapper {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public Review toEntity(ReviewRequest reviewRequest){
        
        Review review = new Review();
        review.setUser(reviewRequest.getUser());
        review.setReviewText(reviewRequest.getReviewText());
        review.setRate(reviewRequest.getRating());

        return review;
    }

    public Review toEntity(ReviewForTempVoteRequest reviewRequest){

        Product product = productRepository.findBySku(reviewRequest.getSku()).orElseThrow();
        
        Review review = new Review();
        review.setUser(reviewRequest.getUser());
        review.setReviewText(reviewRequest.getReviewText());
        review.setRate(reviewRequest.getRating());
        review.setProduct(product);

        return review;
    }

    public Review toEntity(ReviewMessage reviewMessage){

        Product product = productRepository.findBySku(reviewMessage.getSku()).orElseThrow();
        return new Review(
            reviewMessage.getReviewId(),
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

    public List<ReviewMessage> toMessageList(String messages) throws Exception {
        TypeReference<Map<String, List<ReviewMessage>>> mapType = new TypeReference<>() {};
        Map<String, List<ReviewMessage>> response = objectMapper.readValue(messages, mapType);
        return response.get("response");
    }

    public List<Review> toEntityList(List<ReviewMessage> messages) {
        return (messages.stream()
            .map(this::toEntity)
            .collect(Collectors.toList())
        );
    }
}
