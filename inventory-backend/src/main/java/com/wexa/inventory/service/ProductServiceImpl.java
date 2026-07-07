package com.wexa.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wexa.inventory.dto.ProductRequest;
import com.wexa.inventory.dto.ProductResponse;
import com.wexa.inventory.entity.Organization;
import com.wexa.inventory.entity.Product;
import com.wexa.inventory.entity.User;
import com.wexa.inventory.repository.ProductRepository;
import com.wexa.inventory.repository.UserRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProductResponse createProduct(String email,
                                         ProductRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Organization organization = user.getOrganization();

        if (productRepository.existsByOrganizationAndSku(
                organization,
                request.getSku())) {

            throw new RuntimeException("SKU already exists");
        }

        Product product = Product.builder()
                .organization(organization)
                .name(request.getName())
                .sku(request.getSku())
                .description(request.getDescription())
                .quantityOnHand(request.getQuantityOnHand())
                .costPrice(request.getCostPrice())
                .sellingPrice(request.getSellingPrice())
                .lowStockThreshold(request.getLowStockThreshold())
                .build();

        productRepository.save(product);

        return map(product);
    }

    @Override
    public List<ProductResponse> getAllProducts(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return productRepository
                .findByOrganizationOrderByNameAsc(user.getOrganization())
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public ProductResponse getProduct(Long id,
                                      String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getOrganization().getId()
                .equals(user.getOrganization().getId())) {

            throw new RuntimeException("Access denied");
        }

        return map(product);
    }
    @Override
    public ProductResponse updateProduct(Long id,
                                         String email,
                                         ProductRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getOrganization().getId()
                .equals(user.getOrganization().getId())) {

            throw new RuntimeException("Access denied");
        }

        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setDescription(request.getDescription());
        product.setQuantityOnHand(request.getQuantityOnHand());
        product.setCostPrice(request.getCostPrice());
        product.setSellingPrice(request.getSellingPrice());
        product.setLowStockThreshold(request.getLowStockThreshold());

        productRepository.save(product);

        return map(product);
    }

    @Override
    public void deleteProduct(Long id,
                              String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getOrganization().getId()
                .equals(user.getOrganization().getId())) {

            throw new RuntimeException("Access denied");
        }

        productRepository.delete(product);
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