package com.example.backendapi.service;

import com.example.backendapi.domain.NotificationPreference;
import org.springframework.stereotype.Service;

/**
 * PUBLIC_INTERFACE
 * Notification dispatch service (placeholder).
 * Integrate with email and webhook providers as needed; env values will be used by future implementation.
 */
@Service
public class NotificationService {

    // PUBLIC_INTERFACE
    public void notifyPriceDrop(String email, String message, NotificationPreference pref) {
        if (pref.isEmailEnabled()) {
            // TODO: integrate with email provider using MAIL_* env vars
        }
        if (pref.isWebhooksEnabled() && pref.getWebhookUrl() != null) {
            // TODO: send webhook to pref.getWebhookUrl()
        }
    }
}
