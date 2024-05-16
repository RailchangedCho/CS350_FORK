package com.example.fork.global.data.dao;

import com.example.fork.global.data.dto.StampDto;
import com.example.fork.global.data.dto.UserDto;

import java.util.List;

public interface UserDao {

    List<UserDto> getUserList();
    UserDto getUser(String userId);
    UserDto getUserByEmail(String email);
    UserDto getUserByName(String name);
    void addUser(UserDto userDto);
    void editUser(UserDto userDto);
    void deleteUser(String userId);

}
