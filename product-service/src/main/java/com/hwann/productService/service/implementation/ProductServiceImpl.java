package com.hwann.productService.service.implementation;

import com.hwann.productService.dto.ProductRegistrationDto;
import com.hwann.productService.entity.Product;
import com.hwann.productService.repository.ProductRepository;
import com.hwann.productService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product registerProduct(ProductRegistrationDto productRegistrationDto) {
        Product product = Product.builder()
                .name(productRegistrationDto.getName())
                .price(productRegistrationDto.getPrice())
                .description(productRegistrationDto.getDescription())
                .stock(productRegistrationDto.getStock())
                .build();

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }
}
