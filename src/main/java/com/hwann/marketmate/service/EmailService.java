package com.hwann.marketmate.service;

public interface EmailService {
    void sendVerificationCode(String email);
    boolean verifyEmail(String code, String email);
    void updateEmailVerifiedStatus(String email, boolean isVerified) throws Exception;
}