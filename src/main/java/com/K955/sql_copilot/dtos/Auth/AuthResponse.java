package com.K955.sql_copilot.dtos.Auth;

public record AuthResponse(
        String token,
        UserProfileResponse user
) {
}
