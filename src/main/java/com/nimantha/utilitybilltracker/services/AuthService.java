package com.nimantha.utilitybilltracker.services;

import com.nimantha.utilitybilltracker.dto.AuthenticationRequest;
import com.nimantha.utilitybilltracker.dto.AuthenticationResponse;
import com.nimantha.utilitybilltracker.dto.RegisterRequest;
import com.nimantha.utilitybilltracker.dto.RegisterResponse;
import com.nimantha.utilitybilltracker.exceptions.UserAlreadyExistsException;
import com.nimantha.utilitybilltracker.models.User;
import com.nimantha.utilitybilltracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public RegisterResponse register(RegisterRequest request) {
        String username = request.username();
        if (userRepository.findById(username).isPresent()) {
            logger.error("User already exists: {}", username);
            throw new UserAlreadyExistsException("User already exists: " + username);
        }
        User user = User.builder()
                        .username(username)
                        .name(request.name())
                        .password(passwordEncoder.encode(request.password()))
                        .build();
        userRepository.save(user);
        logger.info("User registered successfully: {}", username);
        return new RegisterResponse("Successfully registered.");
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        ));

        var jwtToken = jwtService.generateToken(request.username());
        return new AuthenticationResponse(jwtToken);
    }
}
