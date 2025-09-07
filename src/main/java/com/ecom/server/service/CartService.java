package com.ecom.server.service;

import com.ecom.server.dto.CartItemResponse;
import com.ecom.server.dto.CartRequest;
import com.ecom.server.model.CartItem;

import java.util.List;

public interface CartService {
    void addToCart(String userEmail, CartRequest request);
    void removeFromCart(String userEmail, String productId);
    void updateCartItem(String userEmail, CartRequest request);
    List<CartItemResponse> getCart(String userEmail);
}
