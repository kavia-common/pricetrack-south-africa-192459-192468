package com.example.backendapi.web;

import com.example.backendapi.domain.User;
import com.example.backendapi.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * PUBLIC_INTERFACE
 * Analytics endpoints for Black Friday and retailer behaviors.
 */
@RestController
@RequestMapping("/analytics")
@Tag(name = "Analytics", description = "Black Friday trends and savings analytics")
public class AnalyticsController {

    private final AnalyticsService analytics;

    public AnalyticsController(AnalyticsService analytics) {
        this.analytics = analytics;
    }

    // PUBLIC_INTERFACE
    @GetMapping("/blackfriday-trends")
    @Operation(summary = "Black Friday trends", description = "Aggregated BF trend metrics")
    public Map<String, Object> bf() {
        return analytics.blackFridayTrends();
    }

    // PUBLIC_INTERFACE
    @GetMapping("/retailer-behaviors")
    @Operation(summary = "Retailer behaviors", description = "Retailer behavior insights")
    public Map<String, Object> rb() {
        return analytics.retailerBehaviors();
    }

    // PUBLIC_INTERFACE
    @GetMapping("/savings-summary")
    @Operation(summary = "Savings summary", description = "Savings summary for current user")
    public Map<String, Object> summary(@AuthenticationPrincipal User user) {
        return analytics.savingsSummary(user.getId());
    }
}
