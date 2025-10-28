package com.example.crmplus.order.web;

import com.example.crmplus.order.dto.CreateOrderRequest;
import com.example.crmplus.order.dto.OrderDto;
import com.example.crmplus.order.dto.UpdateOrderStatusRequest;
import com.example.crmplus.order.model.Order;
import com.example.crmplus.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService service;
    public OrderController(OrderService service) { this.service = service; }

    /*@Deprecated
    @GetMapping
    public List<OrderDto> list(@RequestParam(required = false) Long clientId,
                               @RequestParam(required = false) Order.Status status) {
        return service.list(clientId, status);
    }*/

    @GetMapping
    public Page<OrderDto> list(@RequestParam(required = false) Long clientId,
                               @RequestParam(required = false) Order.Status status,
                               @PageableDefault(size = 10, sort = "id",
                                       direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
        return service.list(clientId, status, pageable);
    }

    @GetMapping("/{id}")
    public OrderDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@Valid @RequestBody CreateOrderRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}/status")
    public OrderDto updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateOrderStatusRequest req) {
        return service.updateStatus(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}