package com.example.website.controller;

import org.springframework.web.bind.annotation.*;

import com.example.website.entity.NotificationSettings;
import com.example.website.service.NotificationSettingsService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("*")
public class NotificationSettingsController {

    private final NotificationSettingsService service;

    public NotificationSettingsController(NotificationSettingsService service) {
        this.service = service;
    }

    // GET settings for logged-in user
    @GetMapping
    public NotificationSettings getSettings() {
        return service.getSettings();
    }

    // SAVE / UPDATE settings for logged-in user
    @PostMapping
    public NotificationSettings saveSettings(
            @RequestBody NotificationSettings settings) {

        return service.saveSettings(settings);
    }
}
