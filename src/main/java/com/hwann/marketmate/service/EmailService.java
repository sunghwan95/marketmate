package com.hwann.marketmate.service;

import com.hwann.marketmate.entity.User;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface EmailService {
    String sendVerificationCode(User user) throws Exception;

    Map<String, Boolean> verifyEmail(String code, User user);

    void updateEmailVerifiedStatus(String email, boolean isVerified);
}