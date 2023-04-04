package com.isep.acme.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isep.acme.domain.model.AggregatedRating;
import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.repository.AggregatedRatingRepository;
import com.isep.acme.domain.repository.ProductRepository;
import com.isep.acme.domain.service.AggregatedRatingService;
import com.isep.acme.domain.service.ProductService;
import com.isep.acme.domain.service.ReviewService;

import java.util.Optional;

@Service
public class AggregatedRatingServiceImpl implements AggregatedRatingService{

    @Autowired
    AggregatedRatingRepository arRepository;

    @Autowired
    private ProductRepository pRepository;

    @Autowired
    ReviewService rService;

    @Autowired
    ProductService productService;

    @Override
    public AggregatedRating save( String sku ) {

        Optional<Product> product = pRepository.findBySku( sku );

        if (product.isEmpty()){
            return null;
        }

        Double average = rService.getWeightedAverage(product.get());


        Optional<AggregatedRating> r = arRepository.findByProductId(product.get());
        AggregatedRating aggregateF;

        if(r.isPresent()) {
            r.get().setAverage( average );
            aggregateF = arRepository.save(r.get());
        }
        else {
            AggregatedRating f = new AggregatedRating(average, product.get());
            aggregateF = arRepository.save(f);
        }

        return aggregateF;
    }


}
