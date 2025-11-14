package com.example.backendapi.domain;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * PUBLIC_INTERFACE
 * Represents an application user who can create wishlists and receive notifications.
 */
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_email", columnList = "email", unique = true)
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Column(length = 100)
    private String displayName;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Wishlist> wishlists = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    private NotificationPreference notificationPreference = new NotificationPreference(this);

    protected User() {
    }

    public User(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
        this.notificationPreference = new NotificationPreference(this);
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public Set<Wishlist> getWishlists() { return wishlists; }
    public NotificationPreference getNotificationPreference() { return notificationPreference; }
    public void setNotificationPreference(NotificationPreference notificationPreference) { this.notificationPreference = notificationPreference; }
}
