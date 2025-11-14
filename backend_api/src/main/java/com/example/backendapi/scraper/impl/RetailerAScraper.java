package com.example.backendapi.scraper.impl;

import com.example.backendapi.domain.Product;
import com.example.backendapi.scraper.RetailerScraper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * PUBLIC_INTERFACE
 * Basic stub scraper for RetailerA (placeholder logic).
 */
@Component
public class RetailerAScraper implements RetailerScraper {

    @Value("${app.scraper.retailerA.base-url:https://reta.example.com}")
    private String base;

    @Override
    public String retailerName() {
        return "RetailerA";
    }

    @Override
    public String baseUrl() {
        return base;
    }

    @Override
    public Optional<BigDecimal> fetchPriceFor(Product product) {
        // Placeholder: return a pseudo-random price using product id
        if (product.getId() == null) return Optional.empty();
        BigDecimal price = BigDecimal.valueOf(100 + (product.getId() % 25));
        return Optional.of(price);
    }

    @Override
    public String productUrl(Product product) {
        return base + "/product/" + (product.getSku() != null ? product.getSku() : product.getId());
    }
}
