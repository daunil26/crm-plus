package com.example.crmplus.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CreateOrderRequest {
    @NotNull public Long clientId;
    @NotNull public List<Item> items;

    public static class Item {
        @NotNull public Long productId;
        @Min(1) public int qty;
    }
}