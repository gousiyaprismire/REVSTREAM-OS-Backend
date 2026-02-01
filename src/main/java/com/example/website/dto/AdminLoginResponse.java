package com.example.website.dto;

public class AdminLoginResponse {

    private String message;

    public AdminLoginResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
