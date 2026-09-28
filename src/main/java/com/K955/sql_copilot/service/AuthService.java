package com.K955.sql_copilot.service;

import com.K955.sql_copilot.dtos.Auth.AuthResponse;
import com.K955.sql_copilot.dtos.Auth.LoginRequest;
import com.K955.sql_copilot.dtos.Auth.SignupRequest;

public interface AuthService {

    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);

}
