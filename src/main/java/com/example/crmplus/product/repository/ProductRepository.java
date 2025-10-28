package com.example.crmplus.product.repository;

import com.example.crmplus.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    /*List<Product> findByActive(Boolean active);
    List<Product> findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(String name, String sku);*/
    Page<Product> findByActive(Boolean active, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(String name, String sku, Pageable pageable);
    Optional<Product> findBySku(String sku);
}