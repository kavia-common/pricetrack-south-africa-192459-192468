package com.example.backendapi.domain;

import jakarta.persistence.*;

/**
 * PUBLIC_INTERFACE
 * A retailer/store where products are listed and priced.
 */
@Entity
@Table(name = "retailers", indexes = {
        @Index(name = "idx_retailers_name", columnList = "name", unique = true)
})
public class Retailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 160)
    private String name;

    @Column(length = 255)
    private String websiteUrl;

    protected Retailer() {}

    public Retailer(String name, String websiteUrl) {
        this.name = name;
        this.websiteUrl = websiteUrl;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getWebsiteUrl() { return websiteUrl; }
    public void setName(String name) { this.name = name; }
    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }
}
