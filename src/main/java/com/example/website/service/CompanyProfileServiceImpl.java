package com.example.website.service;

import com.example.website.dto.CompanyProfileRequest;
import com.example.website.dto.CompanyProfileResponse;
import com.example.website.entity.CompanyProfile;
import com.example.website.repository.CompanyProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CompanyProfileServiceImpl implements CompanyProfileService {

    private final CompanyProfileRepository repository;
    private final FileStorageService fileStorageService;

    @Autowired
    public CompanyProfileServiceImpl(
            CompanyProfileRepository repository,
            FileStorageService fileStorageService) {

        this.repository = repository;
        this.fileStorageService = fileStorageService;
    }

    // --------- GET FIRST PROFILE ----------
    @Override
    public CompanyProfileResponse getCompanyProfile() {

        CompanyProfile company = repository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No company profile found"));

        return mapToResponse(company);
    }

    // --------- UPDATE PROFILE DETAILS (GENERAL) ----------
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

    // --------- GET BY TOKEN (EMAIL FROM JWT) ----------
    @Override
    public CompanyProfileResponse getCompanyProfileByToken(String email) {

        CompanyProfile company =
                repository.findByContactEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("Company not found for email: " + email));

        return mapToResponse(company);
    }

    // --------- UPDATE PROFILE BY TOKEN (NEW) ----------
    @Override
    public CompanyProfileResponse updateCompanyProfileByToken(
            String email, CompanyProfileRequest request) {

        CompanyProfile company =
                repository.findByContactEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("Company not found for email: " + email));

        company.setCompanyName(request.getCompanyName());
        company.setAddress(request.getAddress());
        company.setCity(request.getCity());
        company.setCountry(request.getCountry());
        company.setWebsite(request.getWebsite());
        company.setAboutCompany(request.getAboutCompany());

        CompanyProfile saved = repository.save(company);
        return mapToResponse(saved);
    }

    // --------- UPDATE LOGO ----------
    @Override
    public CompanyProfileResponse updateLogo(MultipartFile file) {

        CompanyProfile company = repository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No company profile found"));

        String logoUrl = fileStorageService.saveFile(file);
        company.setLogoUrl(logoUrl);

        CompanyProfile saved = repository.save(company);
        return mapToResponse(saved);
    }

    // --------- MAPPER ----------
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
