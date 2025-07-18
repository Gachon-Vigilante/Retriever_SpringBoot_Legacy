package com.team7.retriever.domain.channel.service;

import com.team7.retriever.domain.channel.controller.dto.response.ChannelDataResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChScrapeService {

	private final RestTemplate restTemplate;

	// 스케줄 1 에서 실행되는 메서드 (channelInfo -> channelScrape)
	public void channelScrape(String channelKey) {
		// String infoAPI = "http://127.0.0.1:5050/telegram/channel/info";
		String api = "http://127.0.0.1:5050/telegram/channel/scrape";

		Map<String, String> requestBody = Map.of("channel_key", channelKey);
		log.debug("\t\t\t\t[ChScrapeService] channel_key: " + channelKey);
		// ResponseEntity<String> infoResponse = restTemplate.postForEntity(infoAPI, requestBody, String.class); // 사용X
		ChannelDataResponse response = restTemplate.postForObject(api, requestBody, ChannelDataResponse.class);
		String message = Objects.requireNonNull(response).getMessage(); // 응답이 비어있을 수 있음
		String status = response.getStatus();
		System.out.println("\t\t\t\t[ChScrapeService] " + message);
		System.out.println("\t\t\t\t[ChScrapeService] 채널 " + channelKey + " 스크랩 상태: " + status);

		log.info("\t\t\t\t[ChScrapeService] " + message);
		log.info("\t\t\t\t[ChScrapeService] 채널 " + channelKey + " 스크랩 상태: " + status);
	}
}
