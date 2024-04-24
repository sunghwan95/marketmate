package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.dto.ProductRegistrationDto;
import com.hwann.marketmate.entity.Product;
import com.hwann.marketmate.repository.ProductRepository;
import com.hwann.marketmate.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public Product registerProduct(ProductRegistrationDto productRegistrationDto) {
        Product product = Product.builder()
                .name(productRegistrationDto.getName())
                .price(productRegistrationDto.getPrice())
                .description(productRegistrationDto.getDescription())
                .stock(productRegistrationDto.getStock())
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
