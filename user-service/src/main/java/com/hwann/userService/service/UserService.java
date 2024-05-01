package com.hwann.userService.service;

import com.hwann.userService.dto.UserRegistrationDto;
import com.hwann.userService.entity.User;
import com.hwann.userService.dto.LoginDto;
import com.hwann.userService.dto.UpdateUserInfoDto;
import com.hwann.userService.dto.UserDetailsDto;
import org.springframework.security.core.Authentication;

public interface UserService {
    void register(UserRegistrationDto userRegistrationDto) throws Exception;

    String login(LoginDto loginDto) throws Exception;

    void logout(String accessToken);

    UserDetailsDto getUserDetails(Long userId);

    void updateUserDetails(User user, UpdateUserInfoDto updateUserInfoDto);

    User identifyUser(Authentication authentication);
}
