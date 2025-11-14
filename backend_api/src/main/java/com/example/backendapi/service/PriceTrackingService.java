package com.example.backendapi.service;

import com.example.backendapi.domain.PriceSnapshot;
import com.example.backendapi.domain.Product;
import com.example.backendapi.domain.Retailer;
import com.example.backendapi.repository.PriceSnapshotRepository;
import com.example.backendapi.repository.ProductRepository;
import com.example.backendapi.repository.RetailerRepository;
import com.example.backendapi.scraper.RetailerScraper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * PUBLIC_INTERFACE
 * Coordinates scraping from retailers and persists price snapshots.
 */
@Service
public class PriceTrackingService {

    private final List<RetailerScraper> scrapers;
    private final ProductRepository productRepository;
    private final RetailerRepository retailerRepository;
    private final PriceSnapshotRepository priceSnapshotRepository;

    public PriceTrackingService(List<RetailerScraper> scrapers,
                                ProductRepository productRepository,
                                RetailerRepository retailerRepository,
                                PriceSnapshotRepository priceSnapshotRepository) {
        this.scrapers = scrapers;
        this.productRepository = productRepository;
        this.retailerRepository = retailerRepository;
        this.priceSnapshotRepository = priceSnapshotRepository;
    }

    // PUBLIC_INTERFACE
    @Transactional
    public void runAll() {
        // Placeholder: iterate all products and scrapers and persist mock price results
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) return;

        for (RetailerScraper scraper : scrapers) {
            Retailer retailer = retailerRepository.findByName(scraper.retailerName())
                    .orElseGet(() -> retailerRepository.save(new Retailer(scraper.retailerName(), scraper.baseUrl())));
            for (Product product : products) {
                // Ask scraper; for now, stubs return fake data
                BigDecimal price = scraper.fetchPriceFor(product).orElse(null);
                if (price == null) continue;
                PriceSnapshot snap = new PriceSnapshot(product, retailer, price);
                snap.setCurrency("ZAR");
                snap.setTimestamp(OffsetDateTime.now());
                snap.setProductUrl(scraper.productUrl(product));
                priceSnapshotRepository.save(snap);
            }
        }
    }
}
