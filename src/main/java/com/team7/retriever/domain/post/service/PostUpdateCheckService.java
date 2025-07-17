package com.team7.retriever.domain.post.service;

import com.team7.retriever.domain.crawling.service.HtmlCrawlingService;
import com.team7.retriever.domain.post.controller.dto.request.PostUpdateCheckRequest;
import com.team7.retriever.domain.crawling.service.PreprocessService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostUpdateCheckService {

	private final PostsService postsService;
	private final HtmlCrawlingService htmlCrawlingService;
	private final PreprocessService preprocessService;
	private final PostSimilarityService postSimilarityService;

	public void updateAllPost() {
		List<PostUpdateCheckRequest> allPosts = postsService.getAllPostsForUpdate();

		for (PostUpdateCheckRequest post : allPosts) {
			updatePost(post);
		}
	}

	public void updatePost(PostUpdateCheckRequest post) {
		String link = post.link();
		String title = post.title();
		String source = post.source();

		log.info("[UpDateCheckService] 크롤링 시작 / link: " + link);
		String newHtml = htmlCrawlingService.crawlHtml(link);
		if (newHtml != null) {
			log.info("[UpDateCheckService] 내용 추출 완료");
			preprocessService.updatePreprocess(newHtml, link, title, source);
			log.info("[UpDateCheckService] 실행 완료");

			// 유사도 모듈 실행 서비스
			log.info("[WebCrawlingService] 유사도 모듈 호출");
			postSimilarityService.calculateSimilarity();
			log.info("[WebCrawlingService] 실행 완료");
		} else {
			log.info("[UpDateCheckService] HTML 크롤링 결과 없음");
		}
	}
}
