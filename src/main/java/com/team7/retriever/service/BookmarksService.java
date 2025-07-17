package com.team7.retriever.service;

import com.team7.retriever.entity.Bookmarks;
import com.team7.retriever.repository.BookmarksRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarksService {

	private final BookmarksRepository BookmarksRepository;

	// 전체 조회
	public List<Bookmarks> getAllBookmarks() {
		return BookmarksRepository.findAll();
	}

	// 아이디로 조회
	public Optional<Bookmarks> getBookmarksById(String id) {
		return BookmarksRepository.findById(id);
	}

	// 북마크 추가
	public void saveBookmark(Bookmarks newBookmark) {
		BookmarksRepository.save(newBookmark);
	}

	// 아이디로 삭제
	public void deleteBookmarkById(String id) {
		BookmarksRepository.deleteById(id);
	}

}
