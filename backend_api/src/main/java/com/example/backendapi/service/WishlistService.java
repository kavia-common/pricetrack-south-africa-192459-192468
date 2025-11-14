package com.example.backendapi.service;

import com.example.backendapi.domain.Product;
import com.example.backendapi.domain.User;
import com.example.backendapi.domain.Wishlist;
import com.example.backendapi.domain.WishlistItem;
import com.example.backendapi.repository.ProductRepository;
import com.example.backendapi.repository.WishlistItemRepository;
import com.example.backendapi.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * PUBLIC_INTERFACE
 * Wishlist management service.
 */
@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository, WishlistItemRepository wishlistItemRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistItemRepository = wishlistItemRepository;
        this.productRepository = productRepository;
    }

    // PUBLIC_INTERFACE
    public List<Wishlist> list(User user) {
        return wishlistRepository.findByUser(user);
    }

    // PUBLIC_INTERFACE
    @Transactional
    public Wishlist create(User user, String name) {
        Wishlist w = new Wishlist(user, name == null || name.isBlank() ? "My Wishlist" : name);
        return wishlistRepository.save(w);
    }

    // PUBLIC_INTERFACE
    @Transactional
    public void delete(User user, Long wishlistId) {
        Wishlist w = wishlistRepository.findById(wishlistId).orElseThrow();
        if (!w.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Not owner of the wishlist");
        }
        wishlistRepository.delete(w);
    }

    // PUBLIC_INTERFACE
    @Transactional
    public WishlistItem addItem(User user, Long wishlistId, Long productId) {
        Wishlist w = wishlistRepository.findById(wishlistId).orElseThrow();
        if (!w.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Not owner of the wishlist");
        }
        Product p = productRepository.findById(productId).orElseThrow();
        var existing = wishlistItemRepository.findByWishlistAndProduct(w, p);
        if (existing.isPresent()) return existing.get();
        WishlistItem wi = new WishlistItem(w, p);
        return wishlistItemRepository.save(wi);
    }

    // PUBLIC_INTERFACE
    @Transactional
    public void removeItem(User user, Long wishlistId, Long itemId) {
        Wishlist w = wishlistRepository.findById(wishlistId).orElseThrow();
        if (!w.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Not owner of the wishlist");
        }
        WishlistItem item = wishlistItemRepository.findById(itemId).orElseThrow();
        if (!item.getWishlist().getId().equals(wishlistId)) throw new IllegalArgumentException("Item not in wishlist");
        wishlistItemRepository.delete(item);
    }

    // PUBLIC_INTERFACE
    @Transactional
    public WishlistItem setTargetPrice(User user, Long wishlistId, Long itemId, BigDecimal targetPrice) {
        Wishlist w = wishlistRepository.findById(wishlistId).orElseThrow();
        if (!w.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Not owner of the wishlist");
        }
        WishlistItem item = wishlistItemRepository.findById(itemId).orElseThrow();
        if (!item.getWishlist().getId().equals(wishlistId)) throw new IllegalArgumentException("Item not in wishlist");
        item.setTargetPrice(targetPrice);
        return wishlistItemRepository.save(item);
    }
}
