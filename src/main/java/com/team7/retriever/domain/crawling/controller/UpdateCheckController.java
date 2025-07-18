package com.team7.retriever.domain.crawling.controller;

import com.team7.retriever.domain.crawling.controller.dto.request.UpdateRequest;
import com.team7.retriever.domain.crawling.controller.dto.request.UpdateCheckRequest;
import com.team7.retriever.domain.post.service.PostsService;
import com.team7.retriever.domain.crawling.service.PreprocessService;
import com.team7.retriever.domain.crawling.service.UpdateCheckService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/post-update")
public class UpdateCheckController {
    private final PostsService postsService;
    private final PreprocessService preprocessService;
    private final UpdateCheckService updateCheckService;

    // DB 데이터 조회 결과 테스트
    @GetMapping("/posts")
    public List<UpdateCheckRequest> getPosts() {
        return postsService.getAllPostsForUpdate();
    }

    @GetMapping
    public void updateAllPostsTest() {
        updateCheckService.updateAllPost();
    }

    // Post 업데이트 테스트
    @PostMapping("/test")
    public String updateTest(@RequestBody UpdateRequest updateRequest) {
        String html = updateRequest.getHtml();
        String link = updateRequest.getLink();
        String title = updateRequest.getTitle();
        String source = updateRequest.getSource();

        return preprocessService.updatePreprocess(html, link, title, source);
    }
}
