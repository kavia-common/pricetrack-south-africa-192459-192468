package com.example.backendapi.repository;

import com.example.backendapi.domain.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * PUBLIC_INTERFACE
 * Repository for Retailer entities.
 */
public interface RetailerRepository extends JpaRepository<Retailer, Long> {
    Optional<Retailer> findByName(String name);
}
