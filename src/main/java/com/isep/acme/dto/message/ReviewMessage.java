package com.isep.acme.dto.message;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.isep.acme.domain.model.enumarate.ApprovalStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMessage {

    private UUID reviewId;
    private ApprovalStatus approvalStatus;
    private String reviewText;
    private String report;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate publishingDate;
    
    private String funFact;
    private String sku;
    private String user;
    private Double rate;

}
