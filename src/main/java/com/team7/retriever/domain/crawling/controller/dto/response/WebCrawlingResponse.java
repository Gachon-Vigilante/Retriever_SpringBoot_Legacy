package com.team7.retriever.domain.crawling.controller.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class WebCrawlingResponse {
	// private List<CrawlGoogleResponse> google;
	private List<String> google;
	private List<String> telegrams;
}
