package com.example.backendapi.domain;

import jakarta.persistence.*;

/**
 * PUBLIC_INTERFACE
 * Describes a product being tracked for prices.
 */
@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_products_sku", columnList = "sku", unique = true),
        @Index(name = "idx_products_name", columnList = "name")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Global SKU/code if available
    @Column(length = 120, unique = true)
    private String sku;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 1024)
    private String description;

    @Column(length = 2048)
    private String imageUrl;

    protected Product() {}

    public Product(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
