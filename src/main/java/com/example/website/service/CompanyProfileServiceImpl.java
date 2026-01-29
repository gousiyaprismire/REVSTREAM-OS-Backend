package com.example.website.service;

import com.example.website.dto.CompanyProfileRequest;
import com.example.website.dto.CompanyProfileResponse;
import com.example.website.entity.CompanyProfile;
import com.example.website.repository.CompanyProfileRepository;
import com.example.website.service.CompanyProfileService;
import org.springframework.stereotype.Service;

@Service
public class CompanyProfileServiceImpl implements CompanyProfileService {

    private final CompanyProfileRepository repository;

    public CompanyProfileServiceImpl(CompanyProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompanyProfileResponse getCompanyProfile() {
        CompanyProfile company = repository.findAll()
                .stream()
                .findFirst()
                .orElse(new CompanyProfile());

        return mapToResponse(company);
    }

    @Override
    public CompanyProfileResponse updateCompanyProfile(CompanyProfileRequest request) {

        CompanyProfile company = repository.findAll()
                .stream()
                .findFirst()
                .orElse(new CompanyProfile());

        company.setCompanyName(request.getCompanyName());
        company.setAddress(request.getAddress());
        company.setCity(request.getCity());
        company.setCountry(request.getCountry());
        company.setWebsite(request.getWebsite());
        company.setContactEmail(request.getContactEmail());
        company.setAboutCompany(request.getAboutCompany());

        CompanyProfile saved = repository.save(company);
        return mapToResponse(saved);
    }

    private CompanyProfileResponse mapToResponse(CompanyProfile company) {
        CompanyProfileResponse response = new CompanyProfileResponse();
        response.setId(company.getId());
        response.setCompanyName(company.getCompanyName());
        response.setAddress(company.getAddress());
        response.setCity(company.getCity());
        response.setCountry(company.getCountry());
        response.setWebsite(company.getWebsite());
        response.setContactEmail(company.getContactEmail());
        response.setAboutCompany(company.getAboutCompany());
        response.setLogoUrl(company.getLogoUrl());
        return response;
    }
}
