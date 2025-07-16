package com.team7.retriever.neo4j.service;

import com.team7.retriever.neo4j.entity.Post;
import com.team7.retriever.neo4j.repository.NeoPostsRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

	private final NeoPostsRepository postsRepository;

	// stream
	public List<Post> getAllPostsStreamed() {
		try (var stream = postsRepository.streamAllWithPromotesAndSimilar()) {
			return stream.collect(Collectors.toList());
		}
	}

}
