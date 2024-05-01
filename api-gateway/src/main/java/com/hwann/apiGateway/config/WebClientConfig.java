package com.hwann.apiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.create("http://user-service"); // user-service는 사용자 정보를 관리하는 마이크로서비스의 주소입니다.
    }
}