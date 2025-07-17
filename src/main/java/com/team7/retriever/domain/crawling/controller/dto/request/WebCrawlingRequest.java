package com.team7.retriever.domain.crawling.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WebCrawlingRequest {
	private List<String> queries;

	@JsonProperty("max_results")
	private int max;
}
