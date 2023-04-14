package com.isep.acme.bootstrapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.repository.ProductRepository;
import com.isep.acme.domain.repository.ReviewRepository;
import com.isep.acme.dto.mapper.ProductMapper;
import com.isep.acme.dto.mapper.ReviewMapper;
import com.isep.acme.dto.message.ProductMessage;
import com.isep.acme.dto.message.ReviewMessage;
import com.isep.acme.messaging.RabbitmqService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ReviewBootstrapper implements CommandLineRunner {

    @Value("#{rpcProductExchange.name}")
    private final String rpcProductExchange;

    @Value("#{rpcReviewExchange.name}")
    private final String rpcReviewExchange;

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    private final RabbitmqService rabbitmqService;
    private final ProductMapper productMapper;
    private final ReviewMapper reviewMapper;

    @Override
    public void run(String... args) throws Exception {
        this.rpcProduct();
        this.rpcReview();
        log.info("Database ready!");
    }

    private void rpcProduct() throws Exception {

        log.info("Sending RPC Product Request...");
        String response = (String) rabbitmqService.sendRpc(rpcProductExchange);
        
        List<ProductMessage> messages = productMapper.toMessageList(response);
        List<Product> products = productMapper.toEntityList(messages);
        log.info("Received RPC Product Response | Size: " + products.size());

        productRepository.saveAll(products);
    }

    private void rpcReview() throws Exception {
        
        log.info("Sending RPC Reviews Request...");
        String response = (String) rabbitmqService.sendRpc(rpcReviewExchange);
        
        List<ReviewMessage> messages = reviewMapper.toMessageList(response);
        List<Review> reviews = reviewMapper.toEntityList(messages);
        log.info("Received RPC Reviews Response | Size: " + reviews.size());

        reviewRepository.saveAll(reviews);
    }
    
}
