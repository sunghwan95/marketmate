package com.hwann.marketmate.service;

import com.hwann.marketmate.dto.*;
import com.hwann.marketmate.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface UserService {
    void register(UserRegistrationDto userRegistrationDto) throws Exception;

    String login(LoginDto loginDto) throws Exception;

    void logout(String accessToken);

    UserDetailsDto getUserDetails(Long userId);

    void updateUserDetails(User user, UpdateUserInfoDto updateUserInfoDto);

    User identifyUser(Authentication authentication);

    User identifyUserById(Long userId);
}
