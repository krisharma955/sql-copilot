package com.K955.sql_copilot.service.impl;

import com.K955.sql_copilot.dtos.Auth.AuthResponse;
import com.K955.sql_copilot.dtos.Auth.LoginRequest;
import com.K955.sql_copilot.dtos.Auth.SignupRequest;
import com.K955.sql_copilot.entity.User;
import com.K955.sql_copilot.exception.BadRequestException;
import com.K955.sql_copilot.exception.ResourceNotFoundException;
import com.K955.sql_copilot.mapper.AuthMapper;
import com.K955.sql_copilot.repository.UserRepository;
import com.K955.sql_copilot.security.JwtAuthUtil;
import com.K955.sql_copilot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthUtil jwtAuthUtil;
    private final AuthMapper authMapper;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthResponse signup(SignupRequest request) {
        Boolean check = userRepository.existsByEmail(request.email());
        if(check) throw new BadRequestException("User with username: " +request.email()+ " already exists.");

        User user = User.builder()
                .email(request.email())
                .password(request.password())
                .build();
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        String token = jwtAuthUtil.generateAccessToken(user);

        return new AuthResponse(token, authMapper.toUserProfileResponse(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException(request.email(), "User"));

        String token = jwtAuthUtil.generateAccessToken(user);

        return new AuthResponse(token, authMapper.toUserProfileResponse(user));
    }

}
