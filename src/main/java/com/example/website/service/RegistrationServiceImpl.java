package com.example.website.service;

import com.example.website.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.website.dto.RegistrationRequest;
import com.example.website.dto.RegistrationResponse;
import com.example.website.repository.UserRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;   // <-- ADD THIS

    public RegistrationServiceImpl(UserRepository repository,
                                   PasswordEncoder passwordEncoder,
                                   EmailService emailService) {   // <-- ADD HERE
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public RegistrationResponse register(RegistrationRequest request) {

        if (request.getPassword() == null || request.getConfirmPassword() == null) {
            return new RegistrationResponse(null, "Password and Confirm Password required");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return new RegistrationResponse(null, "Passwords do not match");
        }

        if (repository.existsByCompanyEmail(request.getCompanyEmail())) {
            return new RegistrationResponse(null, "Email already registered");
        }

        User user = new User();
        user.setCompanyName(request.getCompanyName());
        user.setCompanyEmail(request.getCompanyEmail());
        user.setCompanySize(request.getCompanySize());
        user.setPrimaryStack(request.getPrimaryStack());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User saved = repository.save(user);

        // ✅ SEND EMAIL AFTER SUCCESSFUL REGISTRATION
        emailService.sendRegistrationEmail(
                saved.getCompanyEmail(),
                saved.getCompanyName()
        );

        return new RegistrationResponse(saved.getId(), 
                "User registered successfully. Confirmation email sent.");
    }
}
