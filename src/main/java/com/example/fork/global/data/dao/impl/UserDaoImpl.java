package com.example.fork.global.data.dao.impl;

import com.example.fork.global.data.dao.UserDao;
import com.example.fork.global.data.dto.StampDto;
import com.example.fork.global.data.dto.UserDto;
import com.example.fork.global.data.entity.Stamp;
import com.example.fork.global.data.entity.User;
import com.example.fork.global.data.repository.StampRepository;
import com.example.fork.global.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDaoImpl implements UserDao {

    UserRepository userRepository;

    @Autowired
    public UserDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getUserList() {
        List<UserDto> userDtoList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for (User u : userList) {
            userDtoList.add(u.toDto());
        }
        return userDtoList;
    }

    @Override
    public UserDto getUser(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(User::toDto).orElse(null);
    }

    @Override
    public void addUser(UserDto userDto) {
        if(userRepository.findById(userDto.getId()).isEmpty()) {
            userRepository.save(userDto.toEntity());
        }
    }

    @Override
    public void editUser(UserDto userDto) {
        if(userRepository.findById(userDto.getId()).isPresent()) {
            userRepository.save(userDto.toEntity());
        }
    }

    @Override
    public void deleteUser(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.delete(user);
        }
    }
}
