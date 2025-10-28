package com.example.crmplus.product.service;

import com.example.crmplus.product.dto.CreateProductRequest;
import com.example.crmplus.product.dto.ProductDto;
import com.example.crmplus.product.dto.UpdateProductRequest;
import com.example.crmplus.product.mapper.ProductMapper;
import com.example.crmplus.product.model.Product;
import com.example.crmplus.product.repository.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    /*public List<ProductDto> list(Boolean active, String query) {
        List<Product> source;
        if (query != null && !query.isBlank()) {
            source = repo.findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(query, query);
        } else if (active != null) {
            source = repo.findByActive(active);
        } else {
            source = repo.findAll();
        }
        return source.stream().map(ProductMapper::toDto).toList();
    }*/

    public Page<ProductDto> list(Boolean active, String query, Pageable pageable) {
        Page<Product> src;
        if (query != null && !query.isBlank()) {
            src = repo.findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(query, query, pageable);
        } else if (active != null) {
            src = repo.findByActive(active, pageable);
        } else {
            src = repo.findAll(pageable);
        }
        return src.map(ProductMapper::toDto);
    }

    public ProductDto get(Long id) {
        Product p = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
        return ProductMapper.toDto(p);
    }

    public ProductDto create(CreateProductRequest req) {
        // будем надеяться на уникальный индекс sku; красивый ответ обработаем в ExceptionHandler
        try {
            Product saved = repo.save(ProductMapper.fromCreate(req));
            return ProductMapper.toDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("SKU already exists: " + req.sku);
        }
    }

    public ProductDto update(Long id, UpdateProductRequest req) {
        Product p = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
        // если обновляем sku, можно проверить конфликт
        repo.findBySku(req.sku).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new IllegalArgumentException("SKU already exists: " + req.sku);
            }
        });
        ProductMapper.applyUpdate(p, req);
        Product saved = repo.save(p);
        return ProductMapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Product not found: " + id);
        }
        repo.deleteById(id);
    }
}