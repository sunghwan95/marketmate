package com.hwann.productService.dto;

import lombok.Data;

@Data
public class ProductRegistrationDto {
    public String name;
    public int price;
    public String description;
    public int stock;
}
