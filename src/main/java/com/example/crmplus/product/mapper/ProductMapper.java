package com.example.crmplus.product.mapper;

import com.example.crmplus.product.dto.CreateProductRequest;
import com.example.crmplus.product.dto.ProductDto;
import com.example.crmplus.product.dto.UpdateProductRequest;
import com.example.crmplus.product.model.Product;

public class ProductMapper {

    public static ProductDto toDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.id = p.getId();
        dto.sku = p.getSku();
        dto.name = p.getName();
        dto.price = p.getPrice();
        dto.active = p.getActive();
        return dto;
    }

    public static Product fromCreate(CreateProductRequest r) {
        Product p = new Product();
        p.setSku(r.sku);
        p.setName(r.name);
        p.setPrice(r.price);
        p.setActive(r.active);
        return p;
    }

    public static void applyUpdate(Product p, UpdateProductRequest r) {
        p.setSku(r.sku);
        p.setName(r.name);
        p.setPrice(r.price);
        p.setActive(r.active);
    }
}