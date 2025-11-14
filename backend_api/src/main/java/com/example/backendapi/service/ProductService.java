package com.example.backendapi.service;

import com.example.backendapi.domain.PriceSnapshot;
import com.example.backendapi.domain.Product;
import com.example.backendapi.repository.PriceSnapshotRepository;
import com.example.backendapi.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * PUBLIC_INTERFACE
 * Product search and price history service.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PriceSnapshotRepository priceSnapshotRepository;

    public ProductService(ProductRepository productRepository, PriceSnapshotRepository priceSnapshotRepository) {
        this.productRepository = productRepository;
        this.priceSnapshotRepository = priceSnapshotRepository;
    }

    // PUBLIC_INTERFACE
    public List<Product> search(String q, int page, int size) {
        // Simplistic search: if q provided try SKU match else name contains via stream.
        var all = productRepository.findAll(PageRequest.of(page, size)).getContent();
        if (q == null || q.isBlank()) {
            return all;
        }
        String lower = q.toLowerCase();
        return all.stream().filter(p ->
                (p.getSku() != null && p.getSku().toLowerCase().contains(lower)) ||
                (p.getName() != null && p.getName().toLowerCase().contains(lower)) ||
                (p.getDescription() != null && p.getDescription().toLowerCase().contains(lower))
        ).toList();
    }

    // PUBLIC_INTERFACE
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    // PUBLIC_INTERFACE
    public List<PriceSnapshot> recentPriceHistory(Product product) {
        return priceSnapshotRepository.findTop10ByProductOrderByTimestampDesc(product);
    }
}
