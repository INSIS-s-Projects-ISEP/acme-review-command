package com.isep.acme.dto.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isep.acme.domain.model.Product;
import com.isep.acme.dto.message.ProductMessage;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductMapper {

    private final ObjectMapper objectMapper;
    
    public Product toEntity(ProductMessage productMessage){
        return new Product(productMessage.getSku());
    }

    public List<Product> toEntityList(List<ProductMessage> messages){
        return (messages.stream()
            .map(this::toEntity)
            .collect(Collectors.toList())
        );
    }

    public List<ProductMessage> toMessageList(String messages) throws JsonMappingException, JsonProcessingException{
        TypeReference<Map<String, List<ProductMessage>>> mapType = new TypeReference<>() {};
        Map<String, List<ProductMessage>> response = objectMapper.readValue(messages, mapType);
        return response.get("response");

    }
    
}
