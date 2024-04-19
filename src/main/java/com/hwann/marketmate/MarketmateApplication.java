package com.hwann.marketmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketmateApplication {
	public static void main(String[] args) {
		SpringApplication.run(MarketmateApplication.class, args);
		System.out.println("Spring is running...");
	}
}
