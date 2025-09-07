package com.ecom.server.controller;

import com.ecom.server.dto.CartItemResponse;
import com.ecom.server.dto.CartRequest;
import com.ecom.server.model.CartItem;
import com.ecom.server.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "https://toykart.netlify.app")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(Authentication auth, @RequestBody CartRequest request) {
        cartService.addToCart(auth.getName(), request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(Authentication auth, @PathVariable String productId) {
        cartService.removeFromCart(auth.getName(), productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCart(Authentication auth, @RequestBody CartRequest request) {
        cartService.updateCartItem(auth.getName(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCart(Authentication auth) {
        return ResponseEntity.ok(cartService.getCart(auth.getName()));
    }
}
