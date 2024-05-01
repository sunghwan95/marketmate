package com.hwann.emailService.controller;

import com.hwann.emailService.entity.User;
import com.hwann.emailService.service.implementation.EmailServiceImpl;
import com.hwann.emailService.service.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmailController {
    private UserServiceImpl userService;
    private EmailServiceImpl emailService;

    @PostMapping("/auth/send-verification")
    public ResponseEntity<String> sendVerificationCode(Authentication authentication) throws Exception {
        User user = userService.identifyUser(authentication);
        String userEmail = emailService.sendVerificationCode(user);
        return ResponseEntity.ok("Verification code sent to " + userEmail);
    }

    @PostMapping("/auth/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam(name = "code") String code, Authentication authentication) throws Exception {
        User user = userService.identifyUser(authentication);

        Map<String, Boolean> verificationResult = emailService.verifyEmail(code, user);

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

