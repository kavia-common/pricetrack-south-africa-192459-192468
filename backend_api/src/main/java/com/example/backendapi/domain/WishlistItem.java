package com.example.backendapi.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * PUBLIC_INTERFACE
 * Item on a Wishlist with an optional target price for notifications.
 */
@Entity
@Table(name = "wishlist_items", uniqueConstraints = {
        @UniqueConstraint(name = "uk_wishlist_product", columnNames = { "wishlist_id", "product_id" })
}, indexes = {
        @Index(name = "idx_wishlist_items_product", columnList = "product_id")
})
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Wishlist wishlist;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;

    @Column(precision = 14, scale = 2)
    private BigDecimal targetPrice;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime addedAt = OffsetDateTime.now();

    protected WishlistItem() {}

    public WishlistItem(Wishlist wishlist, Product product) {
        this.wishlist = wishlist;
        this.product = product;
    }

    public Long getId() { return id; }
    public Wishlist getWishlist() { return wishlist; }
    public Product getProduct() { return product; }
    public BigDecimal getTargetPrice() { return targetPrice; }
    public OffsetDateTime getAddedAt() { return addedAt; }
    public void setWishlist(Wishlist wishlist) { this.wishlist = wishlist; }
    public void setProduct(Product product) { this.product = product; }
    public void setTargetPrice(BigDecimal targetPrice) { this.targetPrice = targetPrice; }
}
