package com.ecom.server.service;

import com.ecom.server.dto.ProductDto;
import com.ecom.server.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDto productDto);
    Product updateProduct(String id, ProductDto productDto);
    void deleteProduct(String id);
    List<Product> getAllProducts(List<String> categories, Double minPrice, Double maxPrice, String sort);
    Product getProductById(String id);
    void bulkUpload(List<ProductDto> products);
    List<Product> getProductsByName(String name, List<String> categories, Double minPrice, Double maxPrice, String sort);

}
