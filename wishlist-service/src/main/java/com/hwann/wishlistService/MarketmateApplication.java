package com.hwann.wishlistService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.hwann.marketmate.repository")
public class MarketmateApplication {
	public static void main(String[] args) {
		SpringApplication.run(MarketmateApplication.class, args);
		System.out.println("Spring is running...");
	}
}
