package com.example.website.repository;

import com.example.website.entity.CompanyProfile;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Long> {
	Optional<CompanyProfile> findByContactEmail(String email);
}
