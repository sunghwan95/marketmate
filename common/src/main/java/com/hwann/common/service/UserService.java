package com.hwann.common.service;

import com.hwann.common.dto.LoginDto;
import com.hwann.common.dto.UpdateUserInfoDto;
import com.hwann.common.dto.UserDetailsDto;
import com.hwann.common.dto.UserRegistrationDto;
import com.hwann.common.entity.User;
import com.hwann.marketmate.dto.*;
import org.springframework.security.core.Authentication;

public interface UserService {
    void register(UserRegistrationDto userRegistrationDto) throws Exception;

    String login(LoginDto loginDto) throws Exception;

    void logout(String accessToken);

    UserDetailsDto getUserDetails(Long userId);

    void updateUserDetails(User user, UpdateUserInfoDto updateUserInfoDto);

    User identifyUser(Authentication authentication);
}
