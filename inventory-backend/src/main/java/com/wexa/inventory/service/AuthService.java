package com.wexa.inventory.service;

import com.wexa.inventory.dto.AuthResponse;
import com.wexa.inventory.dto.LoginRequest;
import com.wexa.inventory.dto.SignupRequest;

public interface AuthService {

    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);

}