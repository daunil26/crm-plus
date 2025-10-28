package com.example.crmplus.order.dto;

import com.example.crmplus.order.model.Order;
import jakarta.validation.constraints.NotNull;

public class UpdateOrderStatusRequest {
    @NotNull public Order.Status status; // NEW -> PAID/CANCELLED
}