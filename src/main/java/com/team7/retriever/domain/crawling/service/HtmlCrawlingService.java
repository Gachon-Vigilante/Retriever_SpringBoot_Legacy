package com.team7.retriever.domain.crawling.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team7.retriever.domain.crawling.domain.document.PostHtml;
import com.team7.retriever.domain.crawling.domain.repository.PostHtmlRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class HtmlCrawlingService {

	private final RestTemplate restTemplate;
	private final PostHtmlRepository postHtmlRepository;
	private final ObjectMapper objectMapper;

	@Value("${flask.url}")
	private String flaskUrl;

	public String crawlHtml(String link) {
		String api = flaskUrl + "/crawl/html";
		Map<String, String> requestBody = Map.of("link", link);
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(api, requestBody, String.class);

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				log.info("\t[HtmlCrawlingService] HTML 크롤링 완료");
				JsonNode jsonNode = objectMapper.readTree(response.getBody());
				String html = jsonNode.asText();
				log.info("\t[HtmlCrawlingService] HTML 디코딩 완료");

				return html; // 크롤링 결과 반환
			} else {
				log.warn("\t[HtmlCrawlingService] HTML is null : " + response.getStatusCode());
				throw new RuntimeException("\t[HtmlCrawlingService] HTML is null : " + response.getStatusCode());
			}
		} catch (Exception e) {
			log.error("\t[HtmlCrawlingService] HTML 크롤링 중 오류 발생: " + e.getMessage(), e);
			throw new RuntimeException("\t[HtmlCrawlingService] HTML 크롤링 중 오류 발생: " + e.getMessage(), e);
		}
	}

	// html 저장 (preprocessService.saveData -> saveHtml)
	public void saveHtml(String postId, String html, String url) {
		LocalDateTime now = LocalDateTime.now();
		PostHtml postHtml = PostHtml.builder()
			.postId(postId)
			.html(html)
			.url(url)
			.createdAt(now)
			.updatedAt(now)
			.build();
		postHtmlRepository.save(postHtml);
		log.debug("\t[HtmlCrawlingService] " + url + " saved");
	}
}
