package com.hwann.marketmate.service.impl;

import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.repository.UserRepository;
import com.hwann.marketmate.service.EmailService;
import com.hwann.marketmate.service.UserService;
import com.hwann.marketmate.dto.UserRegistrationDto;
import com.hwann.marketmate.dto.LoginDto;
import com.hwann.marketmate.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final CryptoUtil cryptoUtil;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CryptoUtil cryptoUtil, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cryptoUtil = cryptoUtil;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public void register(UserRegistrationDto userRegistrationDto) throws Exception {
        User user = User.builder()
                .username(userRegistrationDto.username)
                .email(cryptoUtil.encrypt(userRegistrationDto.email))
                .password(passwordEncoder.encode(userRegistrationDto.password))
                .address(cryptoUtil.encrypt(userRegistrationDto.address))
                .phoneNumber(cryptoUtil.encrypt(userRegistrationDto.phoneNumber))
                .emailVerified(false)  // 초기 값 false 설정
                .build();

        userRepository.save(user);
    }


    @Override
    public String login(LoginDto loginDto) {
        // 로그인 로직 구현 (jwt 토큰 발급 등)
        return "token";
    }

    @Override
    public void logout(String token) {
        // 로그아웃 로직 구현
    }

    @Override
    public User updateUserInfo(Long userId, User updateDetails) {
        // 사용자 정보 업데이트 로직 구현
        return updateDetails;
    }
}