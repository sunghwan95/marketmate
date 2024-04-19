package com.hwann.marketmate.service;

import com.hwann.marketmate.entity.User;
import com.hwann.marketmate.dto.UserRegistrationDto;
import com.hwann.marketmate.dto.LoginDto;

public interface UserService {
    void register(UserRegistrationDto userRegistrationDto) throws Exception;

    String login(LoginDto loginDto);

    void logout(String token);

    User updateUserInfo(Long userId, User updateDetails);
}
