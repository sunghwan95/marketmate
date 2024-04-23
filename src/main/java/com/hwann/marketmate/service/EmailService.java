package com.hwann.marketmate.service;

import org.springframework.security.core.Authentication;

import java.util.Map;

public interface EmailService {
    String sendVerificationCode(Authentication authentication) throws Exception;

    Map<String, Boolean> verifyEmail(String code, Authentication authentication);

    void updateEmailVerifiedStatus(String email, boolean isVerified);
}