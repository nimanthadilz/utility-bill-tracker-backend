package com.nimantha.utilitybilltracker.controllers;

import com.nimantha.utilitybilltracker.dto.AuthenticationRequest;
import com.nimantha.utilitybilltracker.dto.AuthenticationResponse;
import com.nimantha.utilitybilltracker.dto.RegisterRequest;
import com.nimantha.utilitybilltracker.dto.RegisterResponse;
import com.nimantha.utilitybilltracker.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }
}
