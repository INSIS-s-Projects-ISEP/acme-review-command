package com.isep.acme.messaging;

import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Review;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReviewProducer {
    private final RabbitmqService rabbitmqService;

    public void reviewCreated(Review review){
        rabbitmqService.sendMessage("review.review-created", "", review);
    }
}