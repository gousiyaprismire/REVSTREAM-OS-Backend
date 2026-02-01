package com.example.website.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.website.entity.NotificationSettings;

public interface NotificationSettingsRepository
        extends JpaRepository<NotificationSettings, Long> {

    Optional<NotificationSettings> findByUserId(Long userId);
}
