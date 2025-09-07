package com.ecom.server.service.impl;

import com.ecom.server.dto.ProductDto;
import com.ecom.server.model.Product;
import com.ecom.server.repository.ProductRepository;
import com.ecom.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(ProductDto dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .rating(dto.getRating())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .stock(dto.getStock())
                .imageUrl(dto.getImageUrl())
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(dto.getName());
        product.setRating(dto.getRating());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts(List<String> categories, Double minPrice, Double maxPrice, String sort) {
        List<Product> products = productRepository.findAll();

        if (categories != null && !categories.isEmpty()) {
            products = products.stream()
                    .filter(p -> categories.contains(p.getCategory()))
                    .collect(Collectors.toList());
        }

        if (minPrice != null && maxPrice != null) {
            products = products.stream()
                    .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }

        // Sorting
        if ("LOW_TO_HIGH".equalsIgnoreCase(sort)) {
            products.sort((a, b) -> Double.compare(a.getPrice(), b.getPrice()));
        } else if ("HIGH_TO_LOW".equalsIgnoreCase(sort)) {
            products.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));
        }

        return products;
    }


    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void bulkUpload(List<ProductDto> products) {
        List<Product> productList = products.stream().map(dto ->
                Product.builder()
                        .name(dto.getName())
                        .rating(dto.getRating())
                        .price(dto.getPrice())
                        .category(dto.getCategory())
                        .stock(dto.getStock())
                        .imageUrl(dto.getImageUrl())
                        .build()
        ).collect(Collectors.toList());
        productRepository.saveAll(productList);
    }

    @Override
    public List<Product> getProductsByName(String name, List<String> categories, Double minPrice, Double maxPrice, String sort) {
        List<Product> products = productRepository.findAll();

        // Filter by name (case-insensitive, contains)
        if (name != null && !name.isEmpty()) {
            products = products.stream()
                    .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Filter by categories (multiple)
        if (categories != null && !categories.isEmpty()) {
            products = products.stream()
                    .filter(p -> categories.contains(p.getCategory()))
                    .collect(Collectors.toList());
        }

        // Filter by price range
        if (minPrice != null && maxPrice != null) {
            products = products.stream()
                    .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }

        // Sorting
        if ("LOW_TO_HIGH".equalsIgnoreCase(sort)) {
            products.sort((a, b) -> Double.compare(a.getPrice(), b.getPrice()));
        } else if ("HIGH_TO_LOW".equalsIgnoreCase(sort)) {
            products.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));
        }

        return products;
    }

}
