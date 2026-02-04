package com.example.website.controller;

import com.example.website.dto.CompanyProfileRequest;
import com.example.website.dto.CompanyProfileResponse;
import com.example.website.service.CompanyProfileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "*")
public class CompanyProfileController {

    private final CompanyProfileService service;

    public CompanyProfileController(CompanyProfileService service) {
        this.service = service;
    }

    // -------- GET COMPANY PROFILE ----------
    @GetMapping("/profile")
    public CompanyProfileResponse getProfile() {
        return service.getCompanyProfile();
    }

    // -------- UPDATE COMPANY PROFILE ----------
    @PutMapping("/profile")
    public CompanyProfileResponse updateProfile(
            @RequestBody CompanyProfileRequest request) {

        return service.updateCompanyProfile(request);
    }

    // -------- UPLOAD & UPDATE LOGO ----------
    @PostMapping("/profile/logo")
    public CompanyProfileResponse uploadLogo(
            @RequestParam("file") MultipartFile file) {

        return service.updateLogo(file);
    }
}
