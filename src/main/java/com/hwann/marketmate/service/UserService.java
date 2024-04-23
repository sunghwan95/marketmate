package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.UpdateUserInfoDto;
import com.hwann.marketmate.dto.UserRegistrationDto;
import com.hwann.marketmate.dto.LoginDto;
import com.hwann.marketmate.dto.WishlistItemDto;
import com.hwann.marketmate.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    void register(UserRegistrationDto userRegistrationDto) throws Exception;

    String login(LoginDto loginDto) throws Exception;

    void logout(String accessToken);

    void updateUserDetails(Long userId, UpdateUserInfoDto updateUserInfoDtd, Authentication authentication) throws Exception;

    User identifyUser(Authentication authentication);
}
