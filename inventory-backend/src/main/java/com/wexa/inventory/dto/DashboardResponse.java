package com.wexa.inventory.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {

    private long totalProducts;

    private long totalQuantity;

    private List<ProductResponse> lowStockProducts;

}