package com.example.backendapi.scheduler;

import com.example.backendapi.service.PriceTrackingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * PUBLIC_INTERFACE
 * Scheduler to periodically run price tracking based on PRICES_CRON env property.
 */
@Configuration
@EnableScheduling
public class PriceScheduler {

    private final PriceTrackingService priceTrackingService;

    public PriceScheduler(PriceTrackingService priceTrackingService) {
        this.priceTrackingService = priceTrackingService;
    }

    // PUBLIC_INTERFACE
    @Scheduled(cron = "${app.scheduler.prices-cron:0 0 * * * *}")
    public void scheduledRun() {
        priceTrackingService.runAll();
    }
}
