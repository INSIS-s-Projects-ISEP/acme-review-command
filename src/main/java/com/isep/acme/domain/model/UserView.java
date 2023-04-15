package com.isep.acme.domain.model;

import java.util.UUID;

import lombok.Data;

@Data
public class UserView {
    UUID userId;

    String username;

    String fullName;
}
