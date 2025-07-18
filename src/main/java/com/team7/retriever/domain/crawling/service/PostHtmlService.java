package com.team7.retriever.domain.crawling.service;

import com.team7.retriever.domain.crawling.domain.repository.PostHtmlRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostHtmlService {

	private final PostHtmlRepository postHtmlRepository;

	public boolean isUrlExists(String url) {
		return postHtmlRepository.existsByUrl(url);
	}
}
