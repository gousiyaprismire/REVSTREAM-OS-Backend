package com.example.website.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.website.dto.ForgotPasswordRequest;
import com.example.website.dto.ResetPasswordRequest;
import com.example.website.service.PasswordResetService;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

    private final PasswordResetService service;

    public PasswordResetController(PasswordResetService service) {
        this.service = service;
    }

    @PostMapping("/forgot")
    public String forgot(@RequestBody ForgotPasswordRequest request) {
        return service.sendResetLink(request.getCompanyEmail());
    }

    @PostMapping("/reset")
    public String reset(@RequestBody ResetPasswordRequest request) {
        return service.resetPassword(
                request.getToken(),
                request.getNewPassword()
        );
    }
}
