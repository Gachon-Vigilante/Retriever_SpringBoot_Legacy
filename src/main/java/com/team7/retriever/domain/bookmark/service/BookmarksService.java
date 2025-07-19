package com.team7.retriever.domain.bookmark.service;

import com.team7.retriever.domain.bookmark.domain.document.Bookmarks;
import com.team7.retriever.domain.bookmark.domain.repository.BookmarksRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarksService {

	private final BookmarksRepository bookmarksRepository;

	// 전체 조회
	public List<Bookmarks> getAllBookmarks() {
		return bookmarksRepository.findAll();
	}

	// 유저 아이디로 조회
	public List<Bookmarks> getBookmarksByUserId(String userId) {
		return bookmarksRepository.findByUserId(userId);
	}

	// 아이디로 조회
	public Optional<Bookmarks> getBookmarksById(String id) {
		return bookmarksRepository.findById(id);
	}

	// 북마크 추가
	public void saveBookmark(Bookmarks newBookmark) {
		bookmarksRepository.save(newBookmark);
	}

	// 아이디로 삭제
	public void deleteBookmarkById(String id) {
		bookmarksRepository.deleteById(id);
	}

}
