package com.wexa.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wexa.inventory.dto.DashboardResponse;
import com.wexa.inventory.dto.ProductResponse;
import com.wexa.inventory.entity.Product;
import com.wexa.inventory.entity.User;
import com.wexa.inventory.repository.ProductRepository;
import com.wexa.inventory.repository.UserRepository;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public DashboardServiceImpl(ProductRepository productRepository,
                                UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DashboardResponse getDashboard(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Product> products =
                productRepository.findByOrganization(user.getOrganization());

        long totalProducts = products.size();

        long totalQuantity = products.stream()
                .mapToLong(Product::getQuantityOnHand)
                .sum();

        List<ProductResponse> lowStockProducts = products.stream()
                .filter(p -> p.getQuantityOnHand()
                        <= p.getLowStockThreshold())
                .map(this::map)
                .toList();

        return DashboardResponse.builder()
                .totalProducts(totalProducts)
                .totalQuantity(totalQuantity)
                .lowStockProducts(lowStockProducts)
                .build();
    }

    private ProductResponse map(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .quantityOnHand(product.getQuantityOnHand())
                .costPrice(product.getCostPrice())
                .sellingPrice(product.getSellingPrice())
                .lowStockThreshold(product.getLowStockThreshold())
                .build();
    }
}