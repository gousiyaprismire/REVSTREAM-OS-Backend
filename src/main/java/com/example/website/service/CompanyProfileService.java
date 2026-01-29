package com.example.website.service;

import com.example.website.dto.CompanyProfileRequest;
import com.example.website.dto.CompanyProfileResponse;

public interface CompanyProfileService {

    CompanyProfileResponse getCompanyProfile();

    CompanyProfileResponse updateCompanyProfile(CompanyProfileRequest request);
}
