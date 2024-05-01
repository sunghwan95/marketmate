package com.hwann.productService.service;

import com.hwann.productService.dto.ProductRegistrationDto;
import com.hwann.productService.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product registerProduct(ProductRegistrationDto productRegistrationDto);

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long productId);
}
