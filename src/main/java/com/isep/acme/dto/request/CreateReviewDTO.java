package com.isep.acme.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CreateReviewDTO {

    private String reviewText;
    private Long userID;
    private Double rating;
    
}
