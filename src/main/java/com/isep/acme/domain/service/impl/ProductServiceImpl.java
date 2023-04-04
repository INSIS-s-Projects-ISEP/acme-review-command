package com.isep.acme.domain.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.repository.ProductRepository;
import com.isep.acme.domain.service.ProductService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return repository.findBySku(sku);
    }

    @Override
    public void deleteBySku(String sku) {
        repository.deleteBySku(sku);
    }

}
