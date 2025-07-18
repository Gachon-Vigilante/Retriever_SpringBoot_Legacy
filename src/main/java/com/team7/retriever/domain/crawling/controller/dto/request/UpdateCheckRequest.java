package com.team7.retriever.domain.crawling.controller.dto.request;

public record UpdateCheckRequest(
	String link,
	String title,
	String source
) {
}
