package com.example.fork.global.data.repository;

import com.example.fork.global.data.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {

    List<Review> findAllByFacilityId(String facilityId);
}
