package com.example.fork.global.data.dao.impl;

import com.example.fork.global.data.dao.BookmarkDao;
import com.example.fork.global.data.dto.BookmarkDto;
import com.example.fork.global.data.dto.ReportDto;
import com.example.fork.global.data.entity.Bookmark;
import com.example.fork.global.data.entity.Report;
import com.example.fork.global.data.repository.BookmarkRepository;
import com.example.fork.global.data.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookmarkDaoImpl implements BookmarkDao {

    BookmarkRepository bookmarkRepository;

    @Autowired
    public BookmarkDaoImpl(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public List<BookmarkDto> getBookmarkList() {
        List<BookmarkDto> bookmarkDtoList = new ArrayList<>();
        List<Bookmark> bookmarkList = bookmarkRepository.findAll();
        for (Bookmark b : bookmarkList) {
            bookmarkDtoList.add(b.toDto());
        }
        return bookmarkDtoList;
    }

    @Override
    public List<BookmarkDto> getBookmarkListByUserId(String userId) {
        List<BookmarkDto> bookmarkDtoList = new ArrayList<>();
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByUserId(userId);
        for (Bookmark b : bookmarkList) {
            bookmarkDtoList.add(b.toDto());
        }
        return bookmarkDtoList;
    }

    @Override
    public BookmarkDto getBookmark(String bookmarkId) {
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findById(bookmarkId);
        return optionalBookmark.map(Bookmark::toDto).orElse(null);
    }

    @Override
    public void addBookmark(BookmarkDto bookmarkDto) {
        if(bookmarkRepository.findById(bookmarkDto.getId()).isEmpty()) {
            bookmarkRepository.save(bookmarkDto.toEntity());
        }
    }

    @Override
    public void deleteBookmark(String bookmarkId) {
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findById(bookmarkId);
        if(optionalBookmark.isPresent()) {
            Bookmark bookmark = optionalBookmark.get();
            bookmarkRepository.delete(bookmark);
        }
    }
}
