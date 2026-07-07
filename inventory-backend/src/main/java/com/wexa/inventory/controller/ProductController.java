package com.wexa.inventory.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.wexa.inventory.dto.ProductRequest;
import com.wexa.inventory.dto.ProductResponse;
import com.wexa.inventory.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponse createProduct(
            @RequestBody ProductRequest request,
            Authentication authentication) {

        return productService.createProduct(
                authentication.getName(),
                request);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts(
            Authentication authentication) {

        return productService.getAllProducts(
                authentication.getName());
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(
            @PathVariable Long id,
            Authentication authentication) {

        return productService.getProduct(
                id,
                authentication.getName());
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request,
            Authentication authentication) {

        return productService.updateProduct(
                id,
                authentication.getName(),
                request);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable Long id,
            Authentication authentication) {

        productService.deleteProduct(
                id,
                authentication.getName());
    }
}