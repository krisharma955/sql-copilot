package com.K955.sql_copilot.dtos.Auth;

import java.util.UUID;

public record UserProfileResponse(
        UUID id,
        String email
) {
}
