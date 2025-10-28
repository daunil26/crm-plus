package com.example.crmplus.order.dto;

import com.example.crmplus.order.model.Order;
import java.math.BigDecimal;
import java.util.List;

public class OrderDto {
    public Long id;
    public Long clientId;
    public Order.Status status;
    public BigDecimal total;
    public List<OrderItemDto> items;

    public static class OrderItemDto {
        public Long productId;
        public int qty;
        public BigDecimal price;
        public BigDecimal lineTotal;
    }
}