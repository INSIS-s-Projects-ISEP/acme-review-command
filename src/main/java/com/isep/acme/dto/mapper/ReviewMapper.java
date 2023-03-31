package com.isep.acme.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.isep.acme.domain.model.Review;
import com.isep.acme.dto.ReviewDTO;

public class ReviewMapper {

    public static ReviewDTO toDto(Review review){
        return new ReviewDTO(review.getIdReview(), review.getReviewText(), review.getPublishingDate(), review.getApprovalStatus(), review.getFunFact(), review.getRating().getRate());
    }

    public static List<ReviewDTO> toDtoList(List<Review> review) {

        List<ReviewDTO> dtoList = new ArrayList<>();
        for (Review rev: review) {
            dtoList.add(toDto(rev));
        }
        
        return dtoList;
    }
}
