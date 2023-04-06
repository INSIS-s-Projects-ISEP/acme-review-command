package com.isep.acme.messaging;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.service.ProductService;
import com.isep.acme.dto.mapper.ProductMapper;
import com.isep.acme.dto.message.ProductMessage;
import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ProductConsumer {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @RabbitListener(queues = {"#{productCreatedQueue.name}"})
    public void productCreated (ProductMessage productMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
        
        log.info("Product received: " + productMessage.getSku());

        Product product = productMapper.toEntity(productMessage);
        productService.create(product);
        channel.basicAck(tag, false);
        
        log.info("Product created: " + product.getSku());
    }

    @RabbitListener(queues = {"#{productDeletedQueue.name}"})
    public void productDeleted(ProductMessage productMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
        log.info("Product received: " + productMessage.getSku());

        Product product = productMapper.toEntity(productMessage);
        productService.deleteBySku(product.getSku());
        channel.basicAck(tag, false);
        
        log.info("Product deleted: " + product.getSku());
    }
}
