package com.ecom.server.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private String rating;
    private double price;
    private String category;
    private int stock;
    private String imageUrl;
}
