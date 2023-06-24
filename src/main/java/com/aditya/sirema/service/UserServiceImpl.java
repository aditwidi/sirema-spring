package com.aditya.sirema.service;

import com.aditya.sirema.dto.UserDto;
import com.aditya.sirema.entity.Role;
import com.aditya.sirema.entity.User;
import com.aditya.sirema.mapper.UserMapper;
import com.aditya.sirema.repository.RoleRepository;
import com.aditya.sirema.repository.UserRepository;
import com.aditya.sirema.util.TbConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aditya.sirema.mapper.UserMapper.mapToUserDto;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        Role role = roleRepository.findByName(TbConstants.Roles.USER);

        if (role == null)
            role = roleRepository.save(new Role(TbConstants.Roles.USER));

        User user;
        user = new User(
                userDto.getName(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                Arrays.asList(role)
        );
        userRepository.save(user);

    }

    @Override
    public void saveStaff(UserDto userDto) {
        Role role = roleRepository.findByName(TbConstants.Roles.ADMIN);

        if (role == null)
            role = roleRepository.save(new Role(TbConstants.Roles.ADMIN));

        User user;
        user = new User(
                userDto.getName(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                Arrays.asList(role)
        );
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map((user) -> (mapToUserDto(user)))
                .collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public List<UserDto> getUsersUser() {
        List<User> users = userRepository.findAll()
                .stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> role.getName().equals(TbConstants.Roles.USER)))
                .collect(Collectors.toList());
        List<UserDto> userDtos = users.stream()
                .map((user) -> (mapToUserDto(user)))
                .collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public int getJmlUser() {
        return getUsersUser().size();
    }
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    @Override
    public UserDto findUserDtoByEmail(String email) {
        return mapToUserDto(userRepository.findByEmail(email)) ;
    }
    @Override
    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }
}

