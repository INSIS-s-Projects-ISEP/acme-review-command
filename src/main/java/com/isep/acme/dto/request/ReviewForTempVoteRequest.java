package com.isep.acme.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewForTempVoteRequest {
    private String sku;
    private String user;
    private String reviewText;
    private Double rating;
    
}
