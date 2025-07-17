package com.team7.retriever.domain.post.controller;

import com.team7.retriever.domain.post.controller.dto.request.PostUpdateRequest;
import com.team7.retriever.domain.post.controller.dto.request.PostUpdateCheckRequest;
import com.team7.retriever.domain.post.service.PostsService;
import com.team7.retriever.domain.crawling.service.PreprocessService;
import com.team7.retriever.domain.post.service.PostUpdateCheckService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/post-update")
public class PostUpdateCheckController {
    private final PostsService postsService;
    private final PreprocessService preprocessService;
    private final PostUpdateCheckService postUpdateCheckService;

    // DB 데이터 조회 결과 테스트
    @GetMapping("/posts")
    public List<PostUpdateCheckRequest> getPosts() {
        return postsService.getAllPostsForUpdate();
    }

    @GetMapping
    public void updateAllPostsTest() {
        postUpdateCheckService.updateAllPost();
    }

    // Post 업데이트 테스트
    @PostMapping("/test")
    public String updateTest(@RequestBody PostUpdateRequest postUpdateRequest) {
        String html = postUpdateRequest.getHtml();
        String link = postUpdateRequest.getLink();
        String title = postUpdateRequest.getTitle();
        String source = postUpdateRequest.getSource();

        return preprocessService.updatePreprocess(html, link, title, source);
    }
}
