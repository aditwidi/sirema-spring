package com.aditya.sirema.service;

import com.aditya.sirema.dto.UserDto;
import com.aditya.sirema.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    void saveStaff(UserDto userDto);

    User findUserByEmail(String email);
    User findUserById(Long userId);
    UserDto findUserDtoByEmail(String email);

    User findUserByName(String name);
    public List<UserDto> getUsers();
    public List<UserDto> getUsersUser();

    int getJmlUser();
}
