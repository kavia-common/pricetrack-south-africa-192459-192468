package com.example.backendapi.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PUBLIC_INTERFACE
 * Auth DTOs for signup/login/refresh.
 */
public class AuthDtos {

    // PUBLIC_INTERFACE
    public record SignupRequest(
            @Schema(description = "Email address", example = "user@example.com")
            String email,
            @Schema(description = "Display name", example = "Lebo")
            String displayName
    ) {}

    // PUBLIC_INTERFACE
    public record LoginRequest(
            @Schema(description = "Email address", example = "user@example.com")
            String email
    ) {}

    // PUBLIC_INTERFACE
    public record TokenResponse(
            @Schema(description = "JWT access token") String accessToken
    ) {}
}
