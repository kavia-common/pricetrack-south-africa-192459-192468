package com.example.backendapi.domain;

import jakarta.persistence.*;

/**
 * PUBLIC_INTERFACE
 * User-level notification preferences to control how and when notifications are sent.
 */
@Entity
@Table(name = "notification_preferences")
public class NotificationPreference {
    @Id
    private Long id;

    @OneToOne(optional = false)
    @MapsId
    private User user;

    @Column(nullable = false)
    private boolean emailEnabled = true;

    @Column(nullable = false)
    private boolean webhooksEnabled = false;

    @Column(length = 2048)
    private String webhookUrl;

    protected NotificationPreference() {}

    public NotificationPreference(User user) {
        this.user = user;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public boolean isEmailEnabled() { return emailEnabled; }
    public boolean isWebhooksEnabled() { return webhooksEnabled; }
    public String getWebhookUrl() { return webhookUrl; }

    public void setUser(User user) { this.user = user; }
    public void setEmailEnabled(boolean emailEnabled) { this.emailEnabled = emailEnabled; }
    public void setWebhooksEnabled(boolean webhooksEnabled) { this.webhooksEnabled = webhooksEnabled; }
    public void setWebhookUrl(String webhookUrl) { this.webhookUrl = webhookUrl; }
}
