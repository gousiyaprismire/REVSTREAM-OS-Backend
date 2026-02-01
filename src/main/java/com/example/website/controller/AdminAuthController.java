package com.example.website.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.website.dto.AdminLoginRequest;
import com.example.website.dto.AdminLoginResponse;
import com.example.website.service.AdminAuthService;

@RestController
@RequestMapping("/admin-auth")
@CrossOrigin("*")
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    public AdminAuthController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(
            @RequestBody AdminLoginRequest request) {

        String message = adminAuthService.login(request);
        return ResponseEntity.ok(new AdminLoginResponse(message));
    }
}
