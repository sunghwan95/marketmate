package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.ProductRegistrationDto;
import com.hwann.marketmate.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product registerProduct(ProductRegistrationDto productRegistrationDto);

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long productId);
}
