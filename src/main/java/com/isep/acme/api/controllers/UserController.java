package com.isep.acme.api.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isep.acme.domain.model.UserView;
import com.isep.acme.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/admin/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public UserView getUser(@PathVariable UUID userId) {

        return userService.getUser(userId);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDetails> create(@PathVariable final String username) {
        UserDetails userDetails = userService.loadUserByUsername(username);

        return ResponseEntity.ok().body(userDetails);
    }
}
