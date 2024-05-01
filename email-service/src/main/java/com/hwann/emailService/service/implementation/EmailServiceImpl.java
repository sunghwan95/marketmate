package com.hwann.emailService.service.implementation;

import com.hwann.emailService.repository.UserRepository;
import com.hwann.emailService.service.EmailService;
import com.hwann.emailService.entity.User;
import com.hwann.emailService.util.CryptoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
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
    public String sendVerificationCode(User user) throws Exception {
        String userEmail = user.getEmail();
        String verificationCode = generateVerificationCode();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(cryptoUtil.decrypt(user.getEmail()));
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + verificationCode);

        mailSender.send(message);

        saveVerificationCode(userEmail, verificationCode);

        return userEmail;
    }

    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    private void saveVerificationCode(String email, String code) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(email, code, 5, TimeUnit.MINUTES);
    }

    @Override
    public Map<String, Boolean> verifyEmail(String code, User user) {
        String userEmail = user.getEmail();
        String savedCode = redisTemplate.opsForValue().get(userEmail);

        Map<String, Boolean> result = new HashMap<>();

        result.put(userEmail, code.equals(savedCode));
        return result;
    }

    @Override
    @Transactional
    public void updateEmailVerifiedStatus(String email, boolean isVerified) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setEmailVerified(isVerified);
        userRepository.save(user);
    }
}
