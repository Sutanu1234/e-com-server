package com.ecom.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemResponse {
    private String productId;
    private String name;
    private String rating;
    private String category;
    private String imageUrl;
    private int quantity;
    private double price;
}
