package com.isep.acme.messaging;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Review;
import com.isep.acme.dto.mapper.ReviewMapper;
import com.isep.acme.dto.message.ReviewForTemporaryVoteMessage;
import com.isep.acme.dto.message.ReviewMessage;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReviewProducer {

    private final RabbitmqService rabbitmqService;
    private final ReviewMapper reviewMapper;

    public void reviewCreated(Review review){
        ReviewMessage reviewMessage = reviewMapper.toMessage(review);
        rabbitmqService.sendMessage("review.review-created", "", reviewMessage);
    }

    public void reviewUpdated(Review review){
        ReviewMessage reviewMessage = reviewMapper.toMessage(review);
        rabbitmqService.sendMessage("review.review-updated", "", reviewMessage);
    }

    public void reviewDeleted(UUID reviewId){
        rabbitmqService.sendMessage("review.review-deleted", "", reviewId);
    }
    
    public void reviewCreatedForTemporaryVote(ReviewForTemporaryVoteMessage reviewCreatedForTemporaryVote){
        rabbitmqService.sendMessage("review.review-created-for-temporary-vote", "", reviewCreatedForTemporaryVote);
    }
}