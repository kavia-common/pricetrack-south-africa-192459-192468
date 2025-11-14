package com.example.backendapi.domain;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * PUBLIC_INTERFACE
 * A list of products a user wants to track.
 */
@Entity
@Table(name = "wishlists", indexes = {
        @Index(name = "idx_wishlists_user", columnList = "user_id")
})
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WishlistItem> items = new LinkedHashSet<>();

    protected Wishlist() {}

    public Wishlist(User user, String name) {
        this.user = user;
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public User getUser() { return user; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public Set<WishlistItem> getItems() { return items; }
    public void setName(String name) { this.name = name; }
    public void setUser(User user) { this.user = user; }
}
