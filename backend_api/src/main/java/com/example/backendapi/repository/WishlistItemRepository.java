package com.example.backendapi.repository;

import com.example.backendapi.domain.Product;
import com.example.backendapi.domain.Wishlist;
import com.example.backendapi.domain.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * PUBLIC_INTERFACE
 * Repository for WishlistItem entities.
 */
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    Optional<WishlistItem> findByWishlistAndProduct(Wishlist wishlist, Product product);
}
