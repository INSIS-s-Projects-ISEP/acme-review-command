package com.isep.acme.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.service.ProductService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ProductConsumer {

    private final ProductService productService;

    @RabbitListener(queues = {"#{productCreatedQueue.name}"})
    public void productCreated(Product product){
        log.info("productReceiver: " + product);
        productService.create(product);
        log.info("Product created: " + product);
    }

    @RabbitListener(queues = {"#{productDeletedQueue.name}"})
    public void productDeleted(String sku){
        log.info("productReceiver: " + sku);
        productService.deleteBySku(sku);
        log.info("Product deleted: " + sku);
    }
}
