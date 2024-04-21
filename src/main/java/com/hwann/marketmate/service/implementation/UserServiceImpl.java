package com.hwann.marketmate.service.implementation;

import com.hwann.marketmate.dto.UpdateUserInfoDto;
import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.repository.UserRepository;
import com.hwann.marketmate.service.UserService;
import com.hwann.marketmate.dto.UserRegistrationDto;
import com.hwann.marketmate.dto.LoginDto;
import com.hwann.marketmate.util.CryptoUtil;
import com.hwann.marketmate.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CryptoUtil cryptoUtil;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate<String, String> stringRedisTemplate;

    @Override
    public void register(UserRegistrationDto userRegistrationDto) throws Exception {
        User user = User.builder()
                .username(userRegistrationDto.username)
                .email(cryptoUtil.encrypt(userRegistrationDto.email))
                .password(passwordEncoder.encode(userRegistrationDto.password))
                .address(cryptoUtil.encrypt(userRegistrationDto.address))
                .phoneNumber(cryptoUtil.encrypt(userRegistrationDto.phoneNumber))
                .emailVerified(false)
                .build();

        userRepository.save(user);
    }

    @Override
    public String login(LoginDto loginDto) throws Exception {
        String userEmail = loginDto.email;
        User user = userRepository.findByEmail(cryptoUtil.encrypt(userEmail))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (passwordEncoder.matches(loginDto.password, user.getPassword())) {
            String accessToken = jwtTokenUtil.generateAccessToken(userEmail);
            String refreshToken = jwtTokenUtil.generateRefreshToken(userEmail);

            stringRedisTemplate.opsForValue().set(cryptoUtil.encrypt(userEmail), refreshToken, 30, TimeUnit.DAYS);

            accessToken = "Bearer " + accessToken;
            return accessToken;
        } else {
            throw new IllegalArgumentException("Password mismatch");
        }
    }

    @Override
    public void logout(String accessToken) {
        try {
            String userEmail = jwtTokenUtil.getEmailFromToken(accessToken.replace("Bearer ", ""));
            String encryptedEmail = cryptoUtil.encrypt(userEmail);

            stringRedisTemplate.delete(encryptedEmail);
        } catch (Exception e) {
            System.out.println("로그아웃 오류: " + e.getMessage());
        }
    }

    @Override
    public void updateUserDetails(String email, UpdateUserInfoDto updateUserInfoDto) throws Exception {
        User user = userRepository.findByEmail(cryptoUtil.encrypt(email))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (updateUserInfoDto.address != null) {
            user.setAddress(cryptoUtil.encrypt(updateUserInfoDto.address));
        }
        if (updateUserInfoDto.phoneNumber != null) {
            user.setPhoneNumber(cryptoUtil.encrypt(updateUserInfoDto.phoneNumber));
        }
        if (updateUserInfoDto.password != null) {
            user.setPassword(cryptoUtil.encrypt(updateUserInfoDto.password));
        }

        userRepository.save(user);
    }
}
