package com.example.website.controller;

import com.example.website.dto.LoginRequest;
import com.example.website.dto.LoginResponse;
import com.example.website.entity.User;
import com.example.website.jwt.JwtUtil;
import com.example.website.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCompanyEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByCompanyEmail(request.getCompanyEmail()).orElseThrow();
        Long userId = user.getId();
        String token = jwtUtil.generateToken(userId, request.getCompanyEmail());

        return new LoginResponse(token, "suuccessful");
    }
}
