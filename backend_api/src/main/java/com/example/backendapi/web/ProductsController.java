package com.example.backendapi.web;

import com.example.backendapi.domain.Product;
import com.example.backendapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * PUBLIC_INTERFACE
 * Products endpoints for search and analytics data.
 */
@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Product search and price history")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    // PUBLIC_INTERFACE
    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Searches products by query")
    public List<Product> search(@RequestParam(name = "q", required = false) String q,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "20") int size) {
        return productService.search(q, page, size);
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(summary = "Get product", description = "Gets product by id")
    public Product get(@PathVariable Long id) {
        return productService.getById(id).orElseThrow();
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}/price-history")
    @Operation(summary = "Recent price history", description = "Returns recent price snapshots for a product")
    public Object history(@PathVariable Long id) {
        Product p = productService.getById(id).orElseThrow();
        return productService.recentPriceHistory(p);
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}/compare")
    @Operation(summary = "Compare across retailers", description = "Placeholder comparison data across retailers")
    public Map<String, Object> compare(@PathVariable Long id) {
        // Placeholder comparison data
        return Map.of("productId", id, "bestRetailer", "RetailerA", "bestPrice", 109.99);
    }
}
