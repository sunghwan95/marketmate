package com.hwann.common.service;

import com.hwann.common.dto.ProductRegistrationDto;
import com.hwann.common.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product registerProduct(ProductRegistrationDto productRegistrationDto);

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long productId);
}
