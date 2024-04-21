package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.repository.UserRepository;
import com.hwann.marketmate.service.EmailService;
import com.hwann.marketmate.util.CryptoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final CryptoUtil cryptoUtil;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void sendVerificationCode(String email) {
        String verificationCode = generateVerificationCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + verificationCode);
        mailSender.send(message);

        saveVerificationCode(email, verificationCode);
    }

    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    private void saveVerificationCode(String email, String code) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(email, code, 5, TimeUnit.MINUTES);
    }

    @Override
    public boolean verifyEmail(String code, String email) {
        String savedCode = redisTemplate.opsForValue().get(email);
        return code.equals(savedCode);
    }

    @Override
    public void updateEmailVerifiedStatus(String email, boolean isVerified) throws Exception {
        User user = userRepository.findByEmail(cryptoUtil.encrypt(email))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setEmailVerified(isVerified);
        userRepository.save(user);
    }
}
