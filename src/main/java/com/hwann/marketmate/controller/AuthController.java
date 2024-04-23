package com.hwann.marketmate.controller;

import com.hwann.marketmate.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private EmailService emailService;

    // 이메일 인증 코드 발송
    @PostMapping("/auth/send-verification")
    public ResponseEntity<String> sendVerificationCode(Authentication authentication) throws Exception {
        String userEmail = emailService.sendVerificationCode(authentication);
        return ResponseEntity.ok("Verification code sent to " + userEmail);
    }

    // 이메일 인증 확인
    @PostMapping("/auth/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam(name = "code") String code, Authentication authentication) throws Exception {
        Map<String, Boolean> verificationResult = emailService.verifyEmail(code, authentication);

        String userEmail = verificationResult.keySet().iterator().next();
        Boolean isVerified = verificationResult.get(userEmail);
        if (isVerified) {
            try {
                emailService.updateEmailVerifiedStatus(userEmail, true);
                return ResponseEntity.ok("Email verified successfully.");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Failed to verify email: " + e.getMessage());
            }
        }
        return ResponseEntity.badRequest().body("Invalid verification code.");
    }
}

