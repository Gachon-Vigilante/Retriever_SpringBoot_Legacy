package com.team7.retriever.domain.post.service;

import com.team7.retriever.domain.crawling.controller.dto.request.UpdateCheckRequest;
import com.team7.retriever.domain.post.controller.dto.response.PostPageResponse;
import com.team7.retriever.domain.post.domain.document.Posts;
import com.team7.retriever.domain.post.domain.repository.PostsRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public PostPageResponse getAllPosts(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Posts> pageResult = postsRepository.findAll(pageable);

		return PostPageResponse.of(pageResult.getTotalElements(), pageResult.getContent());
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

	public List<UpdateCheckRequest> getAllPostsForUpdate() {
		return postsRepository.findAll().stream()
			.filter(post -> !post.isDeleted())
			.map(post -> new UpdateCheckRequest(
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
