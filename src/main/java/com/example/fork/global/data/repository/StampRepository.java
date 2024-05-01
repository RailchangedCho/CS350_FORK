package com.example.fork.global.data.repository;

import com.example.fork.global.data.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampRepository extends JpaRepository<Stamp, String> {
}
