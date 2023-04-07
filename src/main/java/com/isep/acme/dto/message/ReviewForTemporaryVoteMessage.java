package com.isep.acme.dto.message;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewForTemporaryVoteMessage {
    
    private Long reviewId;
    private UUID temporaryVoteId;
    
}