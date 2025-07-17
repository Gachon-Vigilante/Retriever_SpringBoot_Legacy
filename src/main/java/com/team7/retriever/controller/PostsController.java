package com.team7.retriever.controller;

import com.team7.retriever.entity.Posts;
import com.team7.retriever.service.PostsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostsController {

    private final PostsService postsService;

    // 전체 게시글 조회
    @GetMapping("/all")
    public List<Posts> getAllPosts() {
        return postsService.getAllPosts();
    }
    
    /* 241231 추가 */
    // Id로 조회
    @GetMapping("/id/{id}") 
    public Optional<Posts> getPostById(@PathVariable String id) { return postsService.getPostById(id); }

    // 제목에 포함되는 것
    @GetMapping("/title/{title}")
    public List<Posts> getPostsByTitleContaining(@PathVariable String title) {
        return postsService.getPostsByTitleContaining(title);
    }

    // 홍보하는 채널 아이디로 조회
    @GetMapping("/promoChannelId/{promoChannelId}")
    public List<Posts> getPostsByPromoChannelId(@PathVariable String promoChannelId) {
        return postsService.getPostsByPromoChannelId(promoChannelId);
    }

    // 게시글 작성자 이름으로 조회
    @GetMapping("/author/{author}")
    public List<Posts> getPostsByAuthor(@PathVariable String author) {
        return postsService.getPostsByAuthor(author);
    }

    // 해당 링크로 조회 -> 모든 버전 조회 (수집 시점 순)
    @GetMapping("/url")
    public List<Posts> getPostsByLink(@RequestParam String url) {
        return postsService.getPostsByLink(url);
    }
}
