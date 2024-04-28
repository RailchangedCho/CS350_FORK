package com.example.fork.global.data.repository;

import com.example.fork.global.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
