package com.team7.retriever.neo4j.controller;

import com.team7.retriever.neo4j.entity.Posts;
import com.team7.retriever.neo4j.service.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/neo4j/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

    /*
    // 데이터 많으면 실행 X (드라이버 연결 끊어짐)
    @GetMapping
    public List<Posts> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/depth")
    public List<Posts> getPostsDepth1() {
        return postService.getAllPostsDepth1();
    }
     */

    /*
    // paging
    @GetMapping("/paged")
    public List<Posts> getPagedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        return postService.getPagedPosts(page, size);
    }
     */

	@GetMapping("/streamed")
	public List<Posts> getAllPostsStreamed() {
		return postService.getAllPostsStreamed();
	}

}
