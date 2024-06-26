package com.hwann.userService.service.implementation;

import com.hwann.userService.dto.LoginDto;
import com.hwann.userService.dto.UserRegistrationDto;
import com.hwann.userService.entity.User;
import com.hwann.userService.repository.UserRepository;
import com.hwann.userService.service.UserService;
import com.hwann.userService.util.CryptoUtil;
import com.hwann.marketmate.util.JwtTokenUtil;
import com.hwann.userService.dto.UpdateUserInfoDto;
import com.hwann.userService.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CryptoUtil cryptoUtil;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate<String, String> stringRedisTemplate;

    @Override
    @Transactional
    public void register(UserRegistrationDto userRegistrationDto) throws Exception {
        if (userRepository.findByEmail(cryptoUtil.encrypt(userRegistrationDto.getEmail())).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다");
        }

        User user = User.builder()
                .username(userRegistrationDto.getUsername())
                .email(cryptoUtil.encrypt(userRegistrationDto.getEmail()))
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .address(cryptoUtil.encrypt(userRegistrationDto.getAddress()))
                .phoneNumber(cryptoUtil.encrypt(userRegistrationDto.getPhoneNumber()))
                .emailVerified(false)
                .userRole(userRegistrationDto.getRole())
                .build();

        userRepository.save(user);
    }

    @Override
    @Transactional
    public String login(LoginDto loginDto) throws Exception {
        String userEmail = loginDto.email;
        User user = userRepository.findByEmail(cryptoUtil.encrypt(userEmail))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (passwordEncoder.matches(loginDto.password, user.getPassword())) {
            String accessToken = jwtTokenUtil.generateAccessToken(user.getEmail());
            String refreshToken = jwtTokenUtil.generateRefreshToken(user.getEmail());

            stringRedisTemplate.opsForValue().set(user.getEmail(), refreshToken, 30, TimeUnit.DAYS);

            accessToken = "Bearer " + accessToken;
            return accessToken;
        } else {
            throw new IllegalArgumentException("Password mismatch");
        }
    }

    @Override
    @Transactional
    public void logout(String accessToken) {
        try {
            String userEmail = jwtTokenUtil.getEmailFromToken(accessToken.replace("Bearer ", ""));
            String encryptedEmail = cryptoUtil.encrypt(userEmail);

            stringRedisTemplate.delete(encryptedEmail);
            SecurityContextHolder.clearContext();
        } catch (Exception e) {
            System.out.println("로그아웃 오류: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserDetailsDto getUserDetails(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDetailsDto(user.getUserId(), user.getUsername(), user.getEmail());
    }

    @Override
    @Transactional
    public void updateUserDetails(User user, UpdateUserInfoDto updateUserInfoDto) {

        Optional.ofNullable(updateUserInfoDto.getAddress())
                .ifPresent(address -> {
                    try {
                        user.setAddress(cryptoUtil.encrypt(address));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        Optional.ofNullable(updateUserInfoDto.getPhoneNumber())
                .ifPresent(phoneNumber -> {
                    try {
                        user.setPhoneNumber(cryptoUtil.encrypt(phoneNumber));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        Optional.ofNullable(updateUserInfoDto.getPassword())
                .ifPresent(password -> user.setPassword(passwordEncoder.encode(password)));

        userRepository.save(user);
    }

    @Override
    @Transactional
    public User identifyUser(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();

        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
