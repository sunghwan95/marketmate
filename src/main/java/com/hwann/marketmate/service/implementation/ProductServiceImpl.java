package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.dto.ProductDto;
import com.hwann.marketmate.entity.Product;
import com.hwann.marketmate.repository.ProductRepository;
import com.hwann.marketmate.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public Product addProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.name)
                .price(productDto.price)
                .description(productDto.description)
                .stock(productDto.stock)
                .build();

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }
}
