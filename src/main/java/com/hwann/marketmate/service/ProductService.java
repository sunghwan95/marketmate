package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.ProductRegistrationDto;
import com.hwann.marketmate.entity.Product;

import java.util.List;

public interface ProductService {
    Product registerProduct(ProductRegistrationDto productRegistrationDto);

    List<Product> getAllProducts();

    Product getProductById(Long productId);

    Product findProductById(Long productId);
}
