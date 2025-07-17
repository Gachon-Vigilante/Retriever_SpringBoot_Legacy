package com.team7.retriever.service;

import com.team7.retriever.repository.PostHtmlRepository;

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
