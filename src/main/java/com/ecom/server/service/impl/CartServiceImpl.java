package com.ecom.server.service.impl;

import com.ecom.server.dto.CartItemResponse;
import com.ecom.server.dto.CartRequest;
import com.ecom.server.model.CartItem;
import com.ecom.server.model.Product;
import com.ecom.server.model.User;
import com.ecom.server.repository.ProductRepository;
import com.ecom.server.repository.UserRepository;
import com.ecom.server.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public void addToCart(String userEmail, CartRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<CartItem> cart = user.getCart();
        CartItem existing = cart.stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst().orElse(null);

        int newQuantity = request.getQuantity();
        if (existing != null) {
            newQuantity += existing.getQuantity();
        }

        if (newQuantity > product.getStock()) {
            throw new RuntimeException("Cannot add more than available stock: " + product.getStock());
        }

        if (existing != null) {
            existing.setQuantity(newQuantity);
        } else {
            cart.add(new CartItem(request.getProductId(), newQuantity));
        }

        userRepository.save(user);
    }

    @Override
    public void updateCartItem(String userEmail, CartRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (request.getQuantity() > product.getStock()) {
            throw new RuntimeException("Cannot set quantity more than available stock: " + product.getStock());
        }

        user.getCart().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst()
                .ifPresent(item -> item.setQuantity(request.getQuantity()));

        userRepository.save(user);
    }

    @Override
    public void removeFromCart(String userEmail, String productId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getCart().removeIf(item -> item.getProductId().equals(productId));
        userRepository.save(user);
    }

    @Override
    public List<CartItemResponse> getCart(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cart = user.getCart();

        return cart.stream()
                .map(cartItem -> productRepository.findById(cartItem.getProductId())
                        .map(product -> new CartItemResponse(
                                product.getId(),
                                product.getName(),
                                product.getRating(),
                                product.getCategory(),
                                product.getImageUrl(),
                                cartItem.getQuantity(),
                                product.getPrice()
                        ))
                        .orElse(null)) // ignore deleted products
                .filter(response -> response != null)
                .collect(Collectors.toList());
    }

}
