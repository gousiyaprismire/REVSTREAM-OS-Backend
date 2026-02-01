package com.example.website.service;

import com.example.website.entity.NotificationSettings;

public interface NotificationSettingsService {

    NotificationSettings getSettings(Long userId);

    NotificationSettings saveSettings(Long userId, NotificationSettings settings);
}
