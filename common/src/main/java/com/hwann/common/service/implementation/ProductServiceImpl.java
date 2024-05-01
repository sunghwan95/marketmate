package com.hwann.common.service.implementation;

import com.hwann.common.dto.ProductRegistrationDto;
import com.hwann.common.entity.Product;
import com.hwann.common.repository.ProductRepository;
import com.hwann.common.service.ProductService;
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
