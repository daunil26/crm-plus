package com.example.crmplus.product.web;

import com.example.crmplus.product.dto.CreateProductRequest;
import com.example.crmplus.product.dto.ProductDto;
import com.example.crmplus.product.dto.UpdateProductRequest;
import com.example.crmplus.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    /*// GET /api/v1/products?active=true&query=abc
    @GetMapping
    public List<ProductDto> list(@RequestParam(required = false) Boolean active,
                                 @RequestParam(required = false) String query) {
        return service.list(active, query);
    }*/

    @GetMapping
    public Page<ProductDto> list(@RequestParam(required = false) Boolean active,
                                 @RequestParam(required = false) String query,
                                 @PageableDefault(size = 10, sort = "id",
                                         direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
        return service.list(active, query, pageable);
    }

    @GetMapping("/{id}")
    public ProductDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@Valid @RequestBody CreateProductRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public ProductDto update(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}