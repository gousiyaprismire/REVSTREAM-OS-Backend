package com.example.website.repository;

import java.util.Optional;

import com.example.website.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByCompanyEmail(String companyEmail);

    Optional<User> findByCompanyEmail(String companyEmail);
}
