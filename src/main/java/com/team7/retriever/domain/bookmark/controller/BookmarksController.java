package com.team7.retriever.domain.bookmark.controller;

import com.team7.retriever.domain.bookmark.domain.document.Bookmarks;
import com.team7.retriever.domain.bookmark.service.BookmarksService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/bookmarks")
public class BookmarksController {

    private final BookmarksService bookmarksService;

    // 모든 북마크 조회
    @GetMapping("/all") /* 241231 수정 */
    public ResponseEntity<List<Bookmarks>> getAllBookmarks() {
        List<Bookmarks> bookmarks = bookmarksService.getAllBookmarks();
        return ResponseEntity.ok(bookmarks);
    }

    // 특정 유저 ID로 북마크 조회
    @GetMapping("/user")
    public ResponseEntity<List<Bookmarks>> getBookmarksByUserId(@AuthenticationPrincipal String userId) {
        List<Bookmarks> bookmarks = bookmarksService.getBookmarksByUserId(userId);
        return ResponseEntity.ok(bookmarks);
    }

    // 특정 북마크 ID로 북마크 조회
    @GetMapping("/id/{id}") /* 241231 수정 */
    public ResponseEntity<Bookmarks> getBookmarkById(@PathVariable String id) {
        Optional<Bookmarks> bookmark = bookmarksService.getBookmarksById(id);
        return bookmark.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 새로운 북마크 추가
    @PostMapping("{channelId}/add")
    public String addBookmark(@AuthenticationPrincipal String userId, @PathVariable String channelId) {

        // 저장
        bookmarksService.saveBookmark(Bookmarks.create(channelId, userId));

        // test code
        return "Bookmark created successfully";
    }


    // 특정 ID의 북마크 삭제 (북마크 아이디)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable String id) {
        bookmarksService.deleteBookmarkById(id);
        return ResponseEntity.noContent().build();
    }
}
