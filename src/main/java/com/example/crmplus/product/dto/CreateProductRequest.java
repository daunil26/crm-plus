package com.example.crmplus.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreateProductRequest {
    @NotBlank @Size(max = 64)
    public String sku;

    @NotBlank @Size(max = 255)
    public String name;

    @NotNull @DecimalMin(value = "0.0", inclusive = true)
    public BigDecimal price;

    public Boolean active = true;
}