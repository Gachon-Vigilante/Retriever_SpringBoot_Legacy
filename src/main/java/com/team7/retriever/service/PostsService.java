package com.team7.retriever.service;

import com.team7.retriever.domain.post.controller.dto.request.PostUpdateCheckRequest;
import com.team7.retriever.domain.post.domain.document.Posts;
import com.team7.retriever.repository.PostsRepository;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostsService {

	private final PostsRepository postsRepository;

	// 전체 게시글 조회
	public List<Posts> getAllPosts() {
		return postsRepository.findAll();
	}

	/* 241231 추가 */
	// Id로 조회
	public Optional<Posts> getPostById(String id) {
		return postsRepository.findById(id);
	}

	// 제목에 포함되는 것
	public List<Posts> getPostsByTitleContaining(String title) {
		return postsRepository.findByTitleContaining(title);
	}

	// 홍보하는 채널 아이디로 조회
	public List<Posts> getPostsByPromoChannelId(String promoChannelId) {
		return postsRepository.findByPromoChannelId(promoChannelId);
	}

	// 게시글 작성자 이름으로 조회
	public List<Posts> getPostsByAuthor(String author) {
		return postsRepository.findByAuthor(author);
	}

	// 게시글 링크로 조회
	public List<Posts> getPostsByLink(String link) {
		return postsRepository.findByLink(link, Sort.by(Sort.Order.asc("createdAt")));
	}

	public List<PostUpdateCheckRequest> getAllPostsForUpdate() {
		return postsRepository.findAll().stream()
			.filter(post -> !post.isDeleted())
			.map(post -> new PostUpdateCheckRequest(
				post.getLink(),
				post.getTitle(),
				post.getSource()
			))
			.toList();
	}

	public void addChannelId(Posts post) {
		postsRepository.save(post);
	}
}
