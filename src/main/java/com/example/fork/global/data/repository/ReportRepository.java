package com.example.fork.global.data.repository;

import com.example.fork.global.data.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, String> {

    List<Report> findAllByReviewId(String reviewId);
}
