package com.wexa.inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wexa.inventory.entity.Organization;
import com.wexa.inventory.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByOrganization(Organization organization);

    Optional<Product> findByOrganizationAndSku(
            Organization organization,
            String sku);

}