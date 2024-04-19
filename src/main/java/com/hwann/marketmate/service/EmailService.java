package com.hwann.marketmate.service;

public interface EmailService {
    String generateVerificationCode();

    void sendVerificationCode(String email);

    void saveVerificationCode(String email, String code);

    String getSavedVerificationCode(String email);

    boolean verifyEmail(String verificationCode, String email);
}
