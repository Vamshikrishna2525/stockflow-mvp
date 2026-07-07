package com.wexa.inventory.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wexa.inventory.dto.AuthResponse;
import com.wexa.inventory.dto.LoginRequest;
import com.wexa.inventory.dto.SignupRequest;
import com.wexa.inventory.entity.Organization;
import com.wexa.inventory.entity.User;
import com.wexa.inventory.repository.OrganizationRepository;
import com.wexa.inventory.repository.UserRepository;
import com.wexa.inventory.security.JwtService;
import com.wexa.inventory.exception.ResourceAlreadyExistsException;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           OrganizationRepository organizationRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {

        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse signup(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
        	throw new ResourceAlreadyExistsException(
        	        "Email already exists");
        }

        Organization organization = organizationRepository
                .findByName(request.getOrganizationName())
                .orElseGet(() -> {
                    Organization org = Organization.builder()
                            .name(request.getOrganizationName())
                            .build();
                    return organizationRepository.save(org);
                });
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .organization(organization)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, "User registered successfully");
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, "Login successful");
    }
}