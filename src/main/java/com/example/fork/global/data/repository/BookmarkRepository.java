package com.example.fork.global.data.repository;

import com.example.fork.global.data.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, String> {

    List<Bookmark> findAllByUserId(String userId);
}
