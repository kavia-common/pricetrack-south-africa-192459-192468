package com.example.backendapi.scraper.impl;

import com.example.backendapi.domain.Product;
import com.example.backendapi.scraper.RetailerScraper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * PUBLIC_INTERFACE
 * Basic stub scraper for RetailerB (placeholder logic).
 */
@Component
public class RetailerBScraper implements RetailerScraper {

    @Value("${app.scraper.retailerB.base-url:https://retb.example.com}")
    private String base;

    @Override
    public String retailerName() {
        return "RetailerB";
    }

    @Override
    public String baseUrl() {
        return base;
    }

    @Override
    public Optional<BigDecimal> fetchPriceFor(Product product) {
        if (product.getId() == null) return Optional.empty();
        BigDecimal price = BigDecimal.valueOf(150 + (product.getId() % 40));
        return Optional.of(price);
    }

    @Override
    public String productUrl(Product product) {
        return base + "/p/" + (product.getSku() != null ? product.getSku() : product.getId());
    }
}
