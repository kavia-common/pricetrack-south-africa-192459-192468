package com.example.backendapi.repository;

import com.example.backendapi.domain.PriceSnapshot;
import com.example.backendapi.domain.Product;
import com.example.backendapi.domain.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * PUBLIC_INTERFACE
 * Repository for PriceSnapshot entities.
 */
public interface PriceSnapshotRepository extends JpaRepository<PriceSnapshot, Long> {
    List<PriceSnapshot> findTop10ByProductOrderByTimestampDesc(Product product);
    List<PriceSnapshot> findByProductAndRetailerAndTimestampBetween(
            Product product, Retailer retailer, OffsetDateTime start, OffsetDateTime end);
}
