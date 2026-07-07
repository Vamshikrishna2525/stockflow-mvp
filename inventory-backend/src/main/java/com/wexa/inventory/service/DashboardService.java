package com.wexa.inventory.service;

import com.wexa.inventory.dto.DashboardResponse;

public interface DashboardService {

    DashboardResponse getDashboard(String email);

}