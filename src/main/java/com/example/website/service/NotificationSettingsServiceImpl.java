package com.example.website.service;

import org.springframework.stereotype.Service;

import com.example.website.entity.NotificationSettings;
import com.example.website.repository.NotificationSettingsRepository;
import com.example.website.service.NotificationSettingsService;

@Service
public class NotificationSettingsServiceImpl implements NotificationSettingsService {

    private final NotificationSettingsRepository repo;

    public NotificationSettingsServiceImpl(NotificationSettingsRepository repo) {
        this.repo = repo;
    }

    @Override
    public NotificationSettings getSettings(Long userId) {
        return repo.findByUserId(userId)
                .orElseGet(() -> {
                    NotificationSettings s = new NotificationSettings();
                    s.setUserId(userId);
                    s.setAllNotifications(true);
                    s.setFrequency("realtime");
                    return repo.save(s);
                });
    }

    @Override
    public NotificationSettings saveSettings(Long userId, NotificationSettings settings) {
        NotificationSettings existing =
                repo.findByUserId(userId).orElse(new NotificationSettings());

        settings.setId(existing.getId());
        settings.setUserId(userId);

        return repo.save(settings);
    }
}
