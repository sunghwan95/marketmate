package com.hwann.userService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.hwann.marketmate.repository")
public class UserModule {
    public static void main(String[] args) {
        SpringApplication.run(UserModule.class, args);
        System.out.println("User Service is running...");
    }
}
