package com.wexa.inventory.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRequest {

    private String name;

    private String sku;

    private String description;

    private Integer quantityOnHand;

    private BigDecimal costPrice;

    private BigDecimal sellingPrice;

    private Integer lowStockThreshold;

}