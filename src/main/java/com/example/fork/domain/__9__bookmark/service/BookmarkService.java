package com.example.fork.domain.__9__bookmark.service;

import com.example.fork.global.data.dto.BookmarkDto;

import java.util.List;
import java.util.Map;

public interface BookmarkService {

    void addBookmark(String requestUserId, Map<String, Object> requestBody);
    List<BookmarkDto> getBookmarkList(String field, String sort, String userId);
    List<BookmarkDto> getBookmarkListByFacilityId(String field, String sort, String facilityId);
    BookmarkDto getBookmark(String bookmarkId);
    void deleteBookmark(String bookmarkId);
}
