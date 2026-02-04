package com.example.website.service;

import com.example.website.entity.PasswordResetToken;
import com.example.website.entity.User;
import com.example.website.repository.PasswordResetTokenRepository;
import com.example.website.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    public PasswordResetService(UserRepository userRepository,
                                 PasswordResetTokenRepository tokenRepository,
                                 EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    public String sendResetLink(String email) {

        if (!userRepository.existsByCompanyEmail(email)) {
            return "Email not found";
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken =
                new PasswordResetToken(token, email, LocalDateTime.now().plusMinutes(15));

        tokenRepository.save(resetToken);

        String resetLink =
                "http://localhost:3000/reset-password?token=" + token;

        emailService.sendResetPasswordEmail(email, resetLink);

        return "Reset link sent to email";
    }
    
    public String resetPassword(String token, String newPassword) {

        PasswordResetToken resetToken =
                tokenRepository.findByToken(token)
                        .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            return "Token expired";
        }

        User user = userRepository.findByCompanyEmail(resetToken.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(newPassword); // later you should encode it
        userRepository.save(user);

        tokenRepository.delete(resetToken);

        return "Password updated successfully";
    }

}
