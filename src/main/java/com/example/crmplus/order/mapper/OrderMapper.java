package com.example.crmplus.order.mapper;

import com.example.crmplus.order.dto.OrderDto;
import com.example.crmplus.order.model.Order;
import com.example.crmplus.order.model.OrderItem;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDto toDto(Order o) {
        OrderDto dto = new OrderDto();
        dto.id = o.getId();
        dto.clientId = o.getClient().getId();
        dto.status = o.getStatus();
        dto.total = o.getTotal();
        dto.items = o.getItems().stream().map(OrderMapper::toItemDto).collect(Collectors.toList());
        return dto;
    }

    private static OrderDto.OrderItemDto toItemDto(OrderItem it) {
        OrderDto.OrderItemDto d = new OrderDto.OrderItemDto();
        d.productId = it.getProduct().getId();
        d.qty = it.getQty();
        d.price = it.getPrice();
        d.lineTotal = it.getPrice().multiply(BigDecimal.valueOf(it.getQty()));
        return d;
    }
}