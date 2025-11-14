package com.example.backendapi.scraper;

import com.example.backendapi.domain.Product;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * PUBLIC_INTERFACE
 * Abstraction for scraping retailer product prices.
 */
public interface RetailerScraper {

    // PUBLIC_INTERFACE
    String retailerName();

    // PUBLIC_INTERFACE
    String baseUrl();

    // PUBLIC_INTERFACE
    Optional<BigDecimal> fetchPriceFor(Product product);

    // PUBLIC_INTERFACE
    String productUrl(Product product);
}
