package com.example.website.service;

import com.example.website.entity.User;
import com.example.website.expections.ResourceNotFoundException;
import com.example.website.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Invalid hospital");
        }

        return user.get();
    }

}
