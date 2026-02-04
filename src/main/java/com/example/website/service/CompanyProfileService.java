package com.example.website.service;

import com.example.website.dto.CompanyProfileRequest;
import com.example.website.dto.CompanyProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyProfileService {

    // Get first company profile (general)
    CompanyProfileResponse getCompanyProfile();

    // Update company profile details
    CompanyProfileResponse updateCompanyProfile(CompanyProfileRequest request);

    // ✅ NEW — Get company profile using email from JWT token
    CompanyProfileResponse getCompanyProfileByToken(String email);

    // ✅ NEW — Upload and update company logo
    CompanyProfileResponse updateLogo(MultipartFile file);
    CompanyProfileResponse updateCompanyProfileByToken(
            String email, CompanyProfileRequest request);

}
