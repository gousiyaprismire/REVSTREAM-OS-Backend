package com.example.website.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.example.website.entity.NotificationSettings;
import com.example.website.repository.NotificationSettingsRepository;
@Service
public class NotificationSettingsServiceImpl
        implements NotificationSettingsService {

    private final NotificationSettingsRepository repo;

    public NotificationSettingsServiceImpl(NotificationSettingsRepository repo) {
        this.repo = repo;
    }

    @Override
    public NotificationSettings getSettings() {
        // Always return the first (and only) record
        return repo.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    NotificationSettings s = new NotificationSettings();
                    s.setAllNotifications(true);
                    s.setFrequency("realtime");
                    return repo.save(s);
                });
    }

    @Override
    public NotificationSettings saveSettings(NotificationSettings settings) {
        NotificationSettings existing =
                repo.findAll().stream().findFirst().orElse(null);

        if (existing != null) {
            settings.setId(existing.getId());
        }

        return repo.save(settings);
    }
}
