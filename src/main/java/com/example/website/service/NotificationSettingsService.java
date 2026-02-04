package com.example.website.service;

import com.example.website.entity.NotificationSettings;

public interface NotificationSettingsService {

    NotificationSettings getSettings();

    NotificationSettings saveSettings(NotificationSettings settings);
}
