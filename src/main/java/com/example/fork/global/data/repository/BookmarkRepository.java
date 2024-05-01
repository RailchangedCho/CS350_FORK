package com.example.fork.global.data.repository;

import com.example.fork.global.data.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, String> {
}
