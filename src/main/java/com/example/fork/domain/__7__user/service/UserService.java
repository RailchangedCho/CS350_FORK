package com.example.fork.domain.__7__user.service;

import com.example.fork.global.data.dto.ReviewDto;
import com.example.fork.global.data.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface UserService {

    void addUser(String requestUserId, Map<String, Object> requestBody);
    List<UserDto> getUserList(String field, String sort, String userId);
    UserDto getUser(String userId);
    void editUser(String userId, Map<String, Object> requestBody);
    void deleteUser(String userId);
}
