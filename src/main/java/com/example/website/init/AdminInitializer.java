package com.example.website.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.website.entity.Admin;
import com.example.website.repository.AdminRepository;
@Component
public class AdminInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(AdminRepository adminRepository,
                            PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        System.out.println(" AdminInitializer running...");

        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setEmail("admin@revstream.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setActive(true);

            adminRepository.save(admin);

            System.out.println(" Admin inserted");
        } else {
            System.out.println(" Admin already exists");
        }
    }
}
