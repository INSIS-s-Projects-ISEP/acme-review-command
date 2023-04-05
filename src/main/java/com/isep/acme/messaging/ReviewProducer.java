package com.isep.acme.messaging;

import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Review;
import com.isep.acme.dto.mapper.ReviewMapper;
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
}