package com.isep.acme.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ReviewResponse {

    private Long idReview;
    private String reviewText;
    private LocalDate publishingDate;
    private String approvalStatus;
    private String funFact;
    private Double rating;

}
