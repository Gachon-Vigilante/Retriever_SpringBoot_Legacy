package com.team7.retriever.domain.post.controller.dto.request;

public record PostUpdateCheckRequest(
	String link,
	String title,
	String source
) {
}
