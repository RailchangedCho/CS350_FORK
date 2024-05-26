package com.example.fork.domain.__7__user.service.impl;

import com.example.fork.domain.__7__user.service.UserService;
import com.example.fork.global.auth.Permission;
import com.example.fork.global.auth.Type;
import com.example.fork.global.data.dao.ReviewDao;
import com.example.fork.global.data.dao.UserDao;
import com.example.fork.global.data.dto.ReviewDto;
import com.example.fork.global.data.dto.UserDto;
import com.example.fork.global.function.SHA256Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(String requestUserId, Map<String, Object> requestBody) {

        UserDto userDto = UserDto.builder()
                .id(UUID.randomUUID().toString())
                .name(requestBody.get("user_name").toString())
                .password(SHA256Encryptor.encrypt(requestBody.get("user_password").toString()))
                .email(requestBody.get("user_email").toString())
                .deviceId(null)
                .status(true)
                .defaultLanguage("KOR")
                .registerDate(LocalDateTime.now())
                .type(Type.ADMIN)
                .isAuthenticated(true)
                .permission(Permission.PERMISSION)
                .attributes(requestBody.get("user_attributes").toString())
                .build();

        userDao.addUser(userDto);
    }

    @Override
    public List<UserDto> getUserList(String field, String sort, String userId) {

        List<UserDto> responseList = new ArrayList<>();
        responseList = userDao.getUserList();

        if (sort.equals("asc")) {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(UserDto::getRegisterDate))
                        .collect(Collectors.toList());
            }
        }

        else {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(UserDto::getRegisterDate).reversed())
                        .collect(Collectors.toList());
            }
        }

        // TODO : EXCEPTION HANDLING
        return null;
    }

    @Override
    public UserDto getUser(String userId) {
        return userDao.getUser(userId);
    }

    @Override
    public void editUser(String userId, Map<String, Object> requestBody) {

        UserDto userDto = userDao.getUser(userId);
        userDto.setPassword(SHA256Encryptor.encrypt(requestBody.get("user_password").toString()));
        userDto.setDeviceId(requestBody.get("user_device_id").toString());
        userDto.setDefaultLanguage(requestBody.get("user_default_language").toString());
        //reviewDto.setImageId((requestBody.get("facility_description_eng").toString()));

        userDao.editUser(userDto);
    }

    @Override
    public void deleteUser(String userId) {
        userDao.deleteUser(userId);
    }
}
