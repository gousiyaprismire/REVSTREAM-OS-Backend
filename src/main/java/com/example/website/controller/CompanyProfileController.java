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

    // Get company profile (page load)
    @GetMapping("/profile")
    public CompanyProfileResponse getProfile() {
        return service.getCompanyProfile();
    }

    // Update company profile (Save Changes)
    @PutMapping("/profile")
    public CompanyProfileResponse updateProfile(
            @RequestBody CompanyProfileRequest request) {
        return service.updateCompanyProfile(request);
    }
    
    @PostMapping("/profile/logo")
    public String uploadLogo(@RequestParam("file") MultipartFile file) {
        // Save file to /uploads folder or cloud
        return "Logo uploaded successfully";
    }

}
