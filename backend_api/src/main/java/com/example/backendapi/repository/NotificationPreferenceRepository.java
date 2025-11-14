package com.example.backendapi.repository;

import com.example.backendapi.domain.NotificationPreference;
import com.example.backendapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * PUBLIC_INTERFACE
 * Repository for NotificationPreference entities.
 */
public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, Long> {
    Optional<NotificationPreference> findByUser(User user);
}
