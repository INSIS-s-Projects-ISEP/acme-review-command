package com.isep.acme.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ReviewRequest {

    private Long userID;
    private String reviewText;
    private Double rating;
    
}
