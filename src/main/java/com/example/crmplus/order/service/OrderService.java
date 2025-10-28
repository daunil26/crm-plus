package com.example.crmplus.order.service;

import com.example.crmplus.client.model.Client;
import com.example.crmplus.client.repository.ClientRepository;
import com.example.crmplus.order.dto.CreateOrderRequest;
import com.example.crmplus.order.dto.OrderDto;
import com.example.crmplus.order.dto.UpdateOrderStatusRequest;
import com.example.crmplus.order.mapper.OrderMapper;
import com.example.crmplus.order.model.Order;
import com.example.crmplus.order.model.OrderItem;
import com.example.crmplus.order.repository.OrderRepository;
import com.example.crmplus.product.model.Product;
import com.example.crmplus.product.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final ClientRepository clientRepo;
    private final ProductRepository productRepo;

    public OrderService(OrderRepository orderRepo, ClientRepository clientRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.clientRepo = clientRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public OrderDto create(CreateOrderRequest req) {
        Client client = clientRepo.findById(req.clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + req.clientId));

        if (req.items == null || req.items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        Order order = new Order();
        order.setClient(client);
        order.setStatus(Order.Status.NEW);

        BigDecimal total = BigDecimal.ZERO;

        for (CreateOrderRequest.Item it : req.items) {
            Product product = productRepo.findById(it.productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + it.productId));

            if (Boolean.FALSE.equals(product.getActive())) {
                throw new IllegalArgumentException("Product inactive: " + product.getId());
            }

            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(product);
            oi.setQty(it.qty);
            oi.setPrice(product.getPrice()); // «снимок» цены

            order.getItems().add(oi);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(it.qty)));
        }

        order.setTotal(total);
        Order saved = orderRepo.save(order);
        return OrderMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public OrderDto get(Long id) {
        // можно тоже сделать fetch join по id:
        return orderRepo.findById(id)
                .map(OrderMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    /*@Transactional(readOnly = true)
    public List<OrderDto> list(Long clientId, Order.Status status) {
        List<Order> src;
        if (clientId != null) src = orderRepo.findByClientIdWithItemsAndClient(clientId);
        else if (status != null) src = orderRepo.findByStatusWithItemsAndClient(status);
        else src = orderRepo.findAllWithItemsAndClient();
        return src.stream().map(OrderMapper::toDto).toList();
    }*/

    @Transactional(readOnly = true)
    public Page<OrderDto> list(Long clientId, Order.Status status, Pageable pageable) {
        Page<Order> src;
        if (clientId != null)      src = orderRepo.findByClientId(clientId, pageable);
        else if (status != null)   src = orderRepo.findByStatus(status, pageable);
        else                       src = orderRepo.findAll(pageable);
        return src.map(OrderMapper::toDto);
    }

    @Transactional
    public OrderDto updateStatus(Long id, UpdateOrderStatusRequest req) {
        Order o = orderRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

        // простая бизнес-логика состояний
        if (o.getStatus() == Order.Status.CANCELLED || o.getStatus() == Order.Status.PAID) {
            throw new IllegalArgumentException("Order already " + o.getStatus());
        }
        if (req.status == Order.Status.NEW) {
            throw new IllegalArgumentException("Cannot revert to NEW");
        }

        o.setStatus(req.status);
        return OrderMapper.toDto(o);
    }

    public void delete(Long id) {
        if (!orderRepo.existsById(id))
            throw new IllegalArgumentException("Order not found: " + id);
        orderRepo.deleteById(id);
    }
}