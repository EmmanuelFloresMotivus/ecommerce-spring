package com.ecommerce.product_service.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document( value = "product")
public class Product {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
