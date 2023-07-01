package com.aditya.sirema.service;

import com.aditya.sirema.dto.UserDto;
import com.aditya.sirema.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    void saveStaff(UserDto userDto);
    User findUserByEmail(String email);
    User findUserById(Long userId);
}
