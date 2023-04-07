package com.isep.acme.messaging;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.enumarate.ApprovalStatus;
import com.isep.acme.domain.service.ReviewService;
import com.isep.acme.dto.mapper.ReviewMapper;
import com.isep.acme.dto.message.ReviewForTemporaryVoteMessage;
import com.isep.acme.dto.message.TemporaryVoteMessage;
import com.isep.acme.dto.request.ReviewForTempVoteRequest;
import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class TemporaryVoteConsumer {

    private final String instanceId;
    private final MessageConverter messageConverter;
    private final ReviewMapper reviewMapper;
    private final ReviewService reviewService;
    private final ReviewProducer reviewProducer;
    
    @RabbitListener(queues = "#{temporaryVoteCreatedQueue.name}", ackMode = "MANUAL")
    public void temporaryVoteCreated(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        MessageProperties messageProperties = message.getMessageProperties();
        if(messageProperties.getAppId().equals(instanceId)){
            channel.basicAck(tag, false);
            log.info("Received own message and ignore it.");
            return;
        }

        TemporaryVoteMessage temporaryVoteMessage = (TemporaryVoteMessage) messageConverter.fromMessage(message);
        ReviewForTempVoteRequest reviewRequest = temporaryVoteMessage.getReviewRequest();

        Review review = reviewMapper.toEntity(reviewRequest);
        review.setApprovalStatus(ApprovalStatus.APPROVED);

        log.info("Review for Temporary Vote received: " + temporaryVoteMessage.getTemporaryVoteId());
        reviewService.createReviewForProduct(review, review.getProduct());

        reviewProducer.reviewCreated(review);

        ReviewForTemporaryVoteMessage reviewForTempVote = new ReviewForTemporaryVoteMessage();
        reviewForTempVote.setTemporaryVoteId(temporaryVoteMessage.getTemporaryVoteId());
        reviewForTempVote.setReviewId(review.getIdReview());

        reviewProducer.reviewCreatedForTemporaryVote(reviewForTempVote);

        log.info("Review for Temporary Vote created: " + review.getIdReview());
        
    }
    
}
