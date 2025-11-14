package com.example.backendapi.web;

import com.example.backendapi.domain.User;
import com.example.backendapi.domain.Wishlist;
import com.example.backendapi.domain.WishlistItem;
import com.example.backendapi.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * PUBLIC_INTERFACE
 * Wishlist CRUD and item management endpoints.
 */
@RestController
@RequestMapping("/wishlists")
@Tag(name = "Wishlists", description = "Manage wishlists and items")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(summary = "List wishlists", description = "Lists current user's wishlists")
    public List<Wishlist> list(@AuthenticationPrincipal User user) {
        return wishlistService.list(user);
    }

    // PUBLIC_INTERFACE
    @PostMapping
    @Operation(summary = "Create wishlist", description = "Create a new wishlist")
    public Wishlist create(@AuthenticationPrincipal User user, @RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        return wishlistService.create(user, name);
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{wishlistId}")
    @Operation(summary = "Delete wishlist", description = "Delete a wishlist")
    public Map<String, Object> delete(@AuthenticationPrincipal User user, @PathVariable Long wishlistId) {
        wishlistService.delete(user, wishlistId);
        return Map.of("deleted", true);
    }

    // PUBLIC_INTERFACE
    @PostMapping("/{wishlistId}/items")
    @Operation(summary = "Add item", description = "Adds a product to a wishlist")
    public WishlistItem addItem(@AuthenticationPrincipal User user, @PathVariable Long wishlistId, @RequestBody Map<String, Object> payload) {
        Long productId = ((Number) payload.get("productId")).longValue();
        return wishlistService.addItem(user, wishlistId, productId);
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{wishlistId}/items/{itemId}")
    @Operation(summary = "Remove item", description = "Removes item from wishlist")
    public Map<String, Object> removeItem(@AuthenticationPrincipal User user, @PathVariable Long wishlistId, @PathVariable Long itemId) {
        wishlistService.removeItem(user, wishlistId, itemId);
        return Map.of("deleted", true);
    }

    // PUBLIC_INTERFACE
    @PatchMapping("/{wishlistId}/items/{itemId}/target-price")
    @Operation(summary = "Set target price", description = "Sets target price for wishlist item")
    public WishlistItem targetPrice(@AuthenticationPrincipal User user, @PathVariable Long wishlistId, @PathVariable Long itemId, @RequestBody Map<String, Object> payload) {
        BigDecimal target = new BigDecimal(payload.get("targetPrice").toString());
        return wishlistService.setTargetPrice(user, wishlistId, itemId, target);
    }

    // PUBLIC_INTERFACE
    @PatchMapping("/{wishlistId}/notifications")
    @Operation(summary = "Toggle notifications", description = "Placeholder endpoint to toggle wishlist-level notifications (no-op)")
    public Map<String, Object> toggleNotifications(@PathVariable Long wishlistId, @RequestBody Map<String, Object> payload) {
        boolean enabled = payload.getOrDefault("enabled", Boolean.TRUE) instanceof Boolean b ? b : true;
        return Map.of("wishlistId", wishlistId, "notificationsEnabled", enabled);
    }
}
