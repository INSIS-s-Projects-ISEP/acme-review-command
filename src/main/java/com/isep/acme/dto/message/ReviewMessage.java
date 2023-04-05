package com.isep.acme.dto.message;

import java.time.LocalDate;

import com.isep.acme.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ReviewMessage {

    private Long idReview;
    private String approvalStatus;
    private String reviewText;
    private String report;
    private LocalDate publishingDate;
    private String funFact;
    private String sku;
    private User user;
    private Double rate;

}
