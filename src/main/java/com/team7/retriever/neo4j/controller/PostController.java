package com.team7.retriever.neo4j.controller;

import com.team7.retriever.neo4j.entity.Post;
import com.team7.retriever.neo4j.service.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/neo4j/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	@GetMapping("/streamed")
	public List<Post> getAllPostsStreamed() {
		return postService.getAllPostsStreamed();
	}

}
