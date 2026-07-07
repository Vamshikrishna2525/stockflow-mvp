package com.wexa.inventory.service;

import java.util.List;

import com.wexa.inventory.dto.ProductRequest;
import com.wexa.inventory.dto.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(
            String email,
            ProductRequest request);

    List<ProductResponse> getAllProducts(
            String email);

    ProductResponse getProduct(
            Long id,
            String email);

    ProductResponse updateProduct(
            Long id,
            String email,
            ProductRequest request);

    void deleteProduct(
            Long id,
            String email);

}