package com.team7.retriever.service;

import com.team7.retriever.domain.channel.domain.document.ChannelSimilarity;
import com.team7.retriever.domain.channel.domain.repository.ChannelSimilarityRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChSimilarityService {

	private final ChannelSimilarityRepository chSimilarityRepository;

	// 전체 조회
	public List<ChannelSimilarity> getAllChannelSimilarity() {
		List<ChannelSimilarity> allChannels = chSimilarityRepository.findAll();
		allChannels.forEach(this::sortSimilarChannelsBySimilarity);
		return allChannels;
	}

	// 아이디로 조회
	public ChannelSimilarity getChannelSimilarityById(String id) {
		ChannelSimilarity chSimilarity = chSimilarityRepository.findById(id).orElse(null);
		if (chSimilarity != null) {
			sortSimilarChannelsBySimilarity(chSimilarity);
		}
		return chSimilarity;
	}

	// 채널 아이디로 조회
	public ChannelSimilarity getChannelSimilarityByChannelId(long channelId) {
		ChannelSimilarity chSimilarity = chSimilarityRepository.findByChannelId(channelId);
		if (chSimilarity != null) {
			sortSimilarChannelsBySimilarity(chSimilarity);
		}
		return chSimilarity;
	}

	// 유사도 높은 순서로 정렬
	private void sortSimilarChannelsBySimilarity(ChannelSimilarity chSimilarity) {
		chSimilarity.getSimilarChannels().sort((a, b) -> Double.compare(a.getSimilarity(), b.getSimilarity()));
	}
}
