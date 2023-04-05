package com.isep.acme.dto.mapper;

import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Product;
import com.isep.acme.dto.message.ProductMessage;

@Component
public class ProductMapper {
    
    public Product toEntity(ProductMessage productMessage){
        return new Product(productMessage.getSku());
    }
    
}
