package com.isep.acme.domain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.isep.acme.api.controllers.ResourceNotFoundException;
import com.isep.acme.domain.model.Product;
import com.isep.acme.domain.model.Rating;
import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.User;
import com.isep.acme.domain.repository.ProductRepository;
import com.isep.acme.domain.repository.ReviewRepository;
import com.isep.acme.domain.repository.UserRepository;
import com.isep.acme.dto.ReviewResponse;
import com.isep.acme.dto.mapper.ReviewMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final RestService restService;

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    private final ReviewMapper reviewMapper;

    @Override
    public Iterable<Review> getAll() {
        return reviewRepository.findAll();
    }

    public Review createReviewForProduct(Review review, Product product){
        review.setProduct(product);
        return create(review);
    }

    @Override
    public Review create(Review review) {

        LocalDate publishingDate = LocalDate.now();
        String funfact = restService.getFunFact(publishingDate);

        review.setPublishingDate(publishingDate);
        review.setFunFact(funfact);

        return reviewRepository.save(review);
    }

    @Override
    public List<ReviewResponse> getReviewsOfProduct(String sku, String status) {

        Optional<Product> product = productRepository.findBySku(sku);
        if(product.isEmpty()){
            return null;
        }

        Optional<List<Review>> optReview = reviewRepository.findByProductIdStatus(product.get(), status);
        if(optReview.isEmpty()){
            return null;
        }

        return reviewMapper.toResponseList(optReview.get());
    }

    @Override
    public Double getWeightedAverage(Product product){

        Optional<List<Review>> r = reviewRepository.findByProductId(product);
        if(r.isEmpty()){
            return 0.0;
        }

        double sum = 0;
        for(Review rev: r.get()){
            Rating rate = rev.getRating();
            if (rate != null){
                sum += rate.getRate();
            }
        }
        return sum/r.get().size();
    }

    @Override
    public Boolean DeleteReview(Long reviewId)  {

        Optional<Review> rev = reviewRepository.findById(reviewId);
        if (rev.isEmpty()){
            return null;
        }

        Review r = rev.get();
        reviewRepository.delete(r);
        return true;
    }

    @Override
    public List<ReviewResponse> findPendingReview(){

        Optional<List<Review>> r = reviewRepository.findPendingReviews();
        if(r.isEmpty()){
            return null;
        }

        return reviewMapper.toResponseList(r.get());
    }

    @Override
    public ReviewResponse moderateReview(Long reviewID, String approved) throws ResourceNotFoundException, IllegalArgumentException {

        Optional<Review> r = reviewRepository.findById(reviewID);
        if(r.isEmpty()){
            throw new ResourceNotFoundException("Review not found");
        }

        Boolean ap = r.get().setApprovalStatus(approved);
        if(!ap) {
            throw new IllegalArgumentException("Invalid status value");
        }

        Review review = reviewRepository.save(r.get());
        return reviewMapper.toResponse(review);
    }

    @Override
    public List<ReviewResponse> findReviewsByUser(Long userID) {

        Optional<User> user = userRepository.findById(userID);
        if(user.isEmpty()){
            return null;
        }

        Optional<List<Review>> r = reviewRepository.findByUserId(user.get());
        if(r.isEmpty()){
            return null;
        }

        return reviewMapper.toResponseList(r.get());
    }
}