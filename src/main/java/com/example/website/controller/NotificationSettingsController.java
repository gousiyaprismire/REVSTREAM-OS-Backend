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

    // GET SETTINGS
    @GetMapping("/{userId}")
    public NotificationSettings getSettings(@PathVariable Long userId) {
        return service.getSettings(userId);
    }

    // SAVE / UPDATE SETTINGS
    @PostMapping("/{userId}")
    public NotificationSettings saveSettings(
            @PathVariable Long userId,
            @RequestBody NotificationSettings settings) {

        return service.saveSettings(userId, settings);
    }
}
