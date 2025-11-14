package com.example.backendapi.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * PUBLIC_INTERFACE
 * Snapshot of a product price at a retailer at a certain time.
 */
@Entity
@Table(name = "price_snapshots", indexes = {
        @Index(name = "idx_price_snapshots_product_time", columnList = "product_id,timestamp"),
        @Index(name = "idx_price_snapshots_retailer_time", columnList = "retailer_id,timestamp")
})
public class PriceSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Retailer retailer;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal price;

    @Column(length = 8)
    private String currency = "ZAR";

    @Column(nullable = false)
    private OffsetDateTime timestamp = OffsetDateTime.now();

    @Column(length = 2048)
    private String productUrl;

    protected PriceSnapshot() {}

    public PriceSnapshot(Product product, Retailer retailer, BigDecimal price) {
        this.product = product;
        this.retailer = retailer;
        this.price = price;
    }

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public Retailer getRetailer() { return retailer; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
    public OffsetDateTime getTimestamp() { return timestamp; }
    public String getProductUrl() { return productUrl; }
    public void setProduct(Product product) { this.product = product; }
    public void setRetailer(Retailer retailer) { this.retailer = retailer; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
    public void setProductUrl(String productUrl) { this.productUrl = productUrl; }
}
