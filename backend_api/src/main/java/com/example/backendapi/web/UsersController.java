package com.example.backendapi.web;

import com.example.backendapi.domain.NotificationPreference;
import com.example.backendapi.domain.User;
import com.example.backendapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * PUBLIC_INTERFACE
 * User profile and preferences endpoints.
 */
@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User profile and preferences")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    // PUBLIC_INTERFACE
    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Returns current authenticated user profile")
    public Map<String, Object> me(@AuthenticationPrincipal User user) {
        return Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "displayName", user.getDisplayName(),
                "createdAt", user.getCreatedAt()
        );
    }

    // PUBLIC_INTERFACE
    @PatchMapping("/me")
    @Operation(summary = "Update profile", description = "Updates displayName for current user")
    public Map<String, Object> update(@AuthenticationPrincipal User user, @RequestBody Map<String, String> payload) {
        String displayName = payload.get("displayName");
        User updated = userService.updateProfile(user, displayName);
        return Map.of(
                "id", updated.getId(),
                "email", updated.getEmail(),
                "displayName", updated.getDisplayName()
        );
    }

    // PUBLIC_INTERFACE
    @PatchMapping("/me/preferences")
    @Operation(summary = "Update notification preferences", description = "Updates notification preferences")
    public Map<String, Object> updatePrefs(@AuthenticationPrincipal User user, @RequestBody Map<String, Object> payload) {
        boolean emailEnabled = payload.getOrDefault("emailEnabled", Boolean.TRUE) instanceof Boolean b1 ? b1 : true;
        boolean webhooksEnabled = payload.getOrDefault("webhooksEnabled", Boolean.FALSE) instanceof Boolean b2 ? b2 : false;
        String webhookUrl = payload.getOrDefault("webhookUrl", null) instanceof String s ? s : null;

        NotificationPreference pref = userService.updatePreferences(user, emailEnabled, webhooksEnabled, webhookUrl);
        return Map.of(
                "emailEnabled", pref.isEmailEnabled(),
                "webhooksEnabled", pref.isWebhooksEnabled(),
                "webhookUrl", pref.getWebhookUrl()
        );
    }
}
