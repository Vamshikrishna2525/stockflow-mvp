package com.wexa.inventory.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

    private Long id;

    private String name;

    private String sku;

    private String description;

    private Integer quantityOnHand;

    private BigDecimal costPrice;

    private BigDecimal sellingPrice;

    private Integer lowStockThreshold;

}