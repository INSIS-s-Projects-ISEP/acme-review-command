package com.isep.acme.authentication;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class AuthenticationRequest {

    @Email
    @NotNull
    String username;
    
    @NotNull
    String password;
}
