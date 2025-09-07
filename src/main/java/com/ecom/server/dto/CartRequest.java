package com.ecom.server.dto;

import lombok.Data;

@Data
public class CartRequest {
    private String productId;
    private int quantity;
}
