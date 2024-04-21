package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.ProductDto;
import com.hwann.marketmate.entity.Product;

import java.util.List;

public interface ProductService {
    public Product addProduct(ProductDto productDto);

    public List<Product> getAllProducts();

    public Product getProductById(Long productId);
}
