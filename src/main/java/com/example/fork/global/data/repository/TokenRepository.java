package com.example.fork.global.data.repository;

import com.example.fork.global.data.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
}
