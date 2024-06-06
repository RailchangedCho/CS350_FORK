package com.example.fork.domain.__9__bookmark.service.impl;

import com.example.fork.domain.__9__bookmark.service.BookmarkService;
import com.example.fork.global.data.dao.BookmarkDao;
import com.example.fork.global.data.dao.ReviewDao;
import com.example.fork.global.data.dto.BookmarkDto;
import com.example.fork.global.data.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkDao bookmarkDao;

    @Autowired
    public BookmarkServiceImpl(BookmarkDao bookmarkDao) {
        this.bookmarkDao = bookmarkDao;
    }

    @Override
    public void addBookmark(String requestUserId, Map<String, Object> requestBody) {

        BookmarkDto bookmarkDto = BookmarkDto.builder()
                .id(UUID.randomUUID().toString())
                .registerDate(LocalDateTime.now())
                .userId(requestUserId)
                .facilityId(requestBody.get("facility_id").toString())
                .build();

        bookmarkDao.addBookmark(bookmarkDto);
    }

    @Override
    public List<BookmarkDto> getBookmarkList(String field, String sort, String userId) {

        List<BookmarkDto> responseList = new ArrayList<>();
        if (userId.equals("None")) {
            responseList = bookmarkDao.getBookmarkList();
        }
        else {
            responseList = bookmarkDao.getBookmarkListByUserId(userId);
        }

        if (sort.equals("asc")) {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(BookmarkDto::getRegisterDate))
                        .collect(Collectors.toList());
            }
        }

        else {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(BookmarkDto::getRegisterDate).reversed())
                        .collect(Collectors.toList());
            }
        }

        // TODO : EXCEPTION HANDLING
        return null;
    }

    @Override
    public BookmarkDto getBookmark(String bookmarkId) {
        return bookmarkDao.getBookmark(bookmarkId);
    }

    @Override
    public void deleteBookmark(String bookmarkId) {
        bookmarkDao.deleteBookmark(bookmarkId);
    }

    @Override
    public List<BookmarkDto> getBookmarkListByFacilityId(String field, String sort, String facilityId) {

        List<BookmarkDto> responseList = bookmarkDao.getBookmarkListByFacilityId(facilityId);

        if (sort.equals("asc")) {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(BookmarkDto::getRegisterDate))
                        .collect(Collectors.toList());
            }
        }

        else {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(BookmarkDto::getRegisterDate).reversed())
                        .collect(Collectors.toList());
            }
        }

        // TODO : EXCEPTION HANDLING
        return null;
    }
}
