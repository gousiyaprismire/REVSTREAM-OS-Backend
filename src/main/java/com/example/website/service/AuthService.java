package com.example.website.service;

import com.example.website.dto.LoginRequest;
import com.example.website.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
