package com.example.website.controller;

import com.example.website.dto.RegistrationRequest;
import com.example.website.dto.RegistrationResponse;
import com.example.website.service.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
@CrossOrigin("*")
public class RegistrationController {

    @Autowired
    private RegistrationService service;

    @PostMapping
    public RegistrationResponse register(
            @RequestBody RegistrationRequest request
    ) {
        return service.register(request);
    }
}
