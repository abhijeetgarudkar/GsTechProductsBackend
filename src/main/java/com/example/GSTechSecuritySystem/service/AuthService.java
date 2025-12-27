package com.example.GSTechSecuritySystem.service;

import java.util.UUID;

import com.example.GSTechSecuritySystem.exception.InvalidCredentialsException;
import com.example.GSTechSecuritySystem.model.LoginRequest;
import com.example.GSTechSecuritySystem.model.LoginResponse;
import com.example.GSTechSecuritySystem.model.User;
import com.example.GSTechSecuritySystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // BCrypt

    public AuthService(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse loginAsAdmin(LoginRequest request) {
        return loginWithRequiredRole(request, User.Role.ADMIN);
    }

    public LoginResponse loginAsUser(LoginRequest request) {

        System.out.println(">>> SERVICE HIT: loginAsUser()");
        System.out.println(">>> LOOKING FOR USERNAME = [" + request.getUsername() + "]");

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return new LoginResponse(
                user.getUsername(),
                "USER",
                request.getUsername());
    }

    private LoginResponse loginWithRequiredRole(LoginRequest request, User.Role requiredRole) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        if (user.getRole() != requiredRole) {
            throw new RuntimeException("User not authorized for this login type");
        }

        // For now generate a simple random token (replace with JWT if needed)
        String token = UUID.randomUUID().toString();

        return new LoginResponse(token, user.getRole().name(), user.getUsername());
    }
}
