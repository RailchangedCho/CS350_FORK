package com.example.fork.global.data.dao;

import com.example.fork.global.data.dto.BookmarkDto;
import com.example.fork.global.data.dto.ReportDto;

import java.util.List;

public interface BookmarkDao {

    List<BookmarkDto> getBookmarkList();
    List<BookmarkDto> getBookmarkListByUserId(String userId);
    List<BookmarkDto> getBookmarkListByFacilityId(String facilityId);
    BookmarkDto getBookmark(String bookmarkId);
    void addBookmark(BookmarkDto bookmarkDto);
    void deleteBookmark(String bookmarkId);

}
