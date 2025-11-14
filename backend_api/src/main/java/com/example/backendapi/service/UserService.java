package com.example.backendapi.service;

import com.example.backendapi.domain.NotificationPreference;
import com.example.backendapi.domain.User;
import com.example.backendapi.repository.NotificationPreferenceRepository;
import com.example.backendapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * PUBLIC_INTERFACE
 * Business logic for users.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final NotificationPreferenceRepository notificationPreferenceRepository;

    public UserService(UserRepository userRepository, NotificationPreferenceRepository notificationPreferenceRepository) {
        this.userRepository = userRepository;
        this.notificationPreferenceRepository = notificationPreferenceRepository;
    }

    // PUBLIC_INTERFACE
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // PUBLIC_INTERFACE
    @Transactional
    public User signupOrGet(String email, String displayName) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            User u = new User(email, displayName == null ? email : displayName);
            User saved = userRepository.save(u);
            // ensure preference exists (1:1)
            NotificationPreference np = new NotificationPreference(saved);
            notificationPreferenceRepository.save(np);
            saved.setNotificationPreference(np);
            return saved;
        });
    }

    // PUBLIC_INTERFACE
    @Transactional
    public User updateProfile(User user, String newDisplayName) {
        if (newDisplayName != null && !newDisplayName.isBlank()) {
            user.setDisplayName(newDisplayName);
        }
        return userRepository.save(user);
    }

    // PUBLIC_INTERFACE
    @Transactional
    public NotificationPreference updatePreferences(User user, boolean emailEnabled, boolean webhooksEnabled, String webhookUrl) {
        NotificationPreference pref = notificationPreferenceRepository.findByUser(user)
                .orElseGet(() -> new NotificationPreference(user));
        pref.setEmailEnabled(emailEnabled);
        pref.setWebhooksEnabled(webhooksEnabled);
        if (webhookUrl != null) pref.setWebhookUrl(webhookUrl);
        return notificationPreferenceRepository.save(pref);
    }
}
