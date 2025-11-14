package com.example.backendapi.web;

import com.example.backendapi.domain.User;
import com.example.backendapi.security.JwtService;
import com.example.backendapi.service.UserService;
import com.example.backendapi.web.dto.AuthDtos.LoginRequest;
import com.example.backendapi.web.dto.AuthDtos.SignupRequest;
import com.example.backendapi.web.dto.AuthDtos.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * PUBLIC_INTERFACE
 * Auth endpoints: signup, login, refresh.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication endpoints")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    // PUBLIC_INTERFACE
    @PostMapping("/signup")
    @Operation(summary = "Signup", description = "Creates an account if not exists and returns a JWT")
    public ResponseEntity<TokenResponse> signup(@Valid @RequestBody SignupRequest req) {
        if (req.email() == null || req.email().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        User user = userService.signupOrGet(req.email(), req.displayName());
        String token = jwtService.generateToken(user.getEmail(), Map.of("uid", user.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenResponse(token));
        // In production, password-based or OAuth flows should be implemented.
    }

    // PUBLIC_INTERFACE
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Logs in an existing user by email only (demo) and returns a JWT")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        if (req.email() == null || req.email().isBlank()) return ResponseEntity.badRequest().build();
        User user = userService.getByEmail(req.email())
                .orElse(null);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String token = jwtService.generateToken(user.getEmail(), Map.of("uid", user.getId()));
        return ResponseEntity.ok(new TokenResponse(token));
    }

    // PUBLIC_INTERFACE
    @PostMapping("/refresh")
    @Operation(summary = "Refresh token", description = "Refreshes access token (demo: generates new token for same subject)")
    public ResponseEntity<TokenResponse> refresh(@RequestHeader(name = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String token = authHeader.substring(7);
        String sub;
        try {
            sub = jwtService.extractSubject(token);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userService.getByEmail(sub).orElse(null);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String newToken = jwtService.generateToken(user.getEmail(), Map.of("uid", user.getId()));
        return ResponseEntity.ok(new TokenResponse(newToken));
    }
}
