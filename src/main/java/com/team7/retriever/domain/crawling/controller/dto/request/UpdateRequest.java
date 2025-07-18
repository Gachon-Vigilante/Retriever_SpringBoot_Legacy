package com.team7.retriever.domain.crawling.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateRequest {
	private String html;
	private String link;
	private String title;
	private String source;
}
