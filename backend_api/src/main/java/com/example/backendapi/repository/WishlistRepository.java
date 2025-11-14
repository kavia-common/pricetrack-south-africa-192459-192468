package com.example.backendapi.repository;

import com.example.backendapi.domain.User;
import com.example.backendapi.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * PUBLIC_INTERFACE
 * Repository for Wishlist entities.
 */
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);
}
