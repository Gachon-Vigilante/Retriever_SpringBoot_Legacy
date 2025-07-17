package com.team7.retriever.service;

import com.team7.retriever.entity.ChData;
import com.team7.retriever.repository.ChDataRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChDataService {

	private final ChDataRepository chDataRepository;

	// 전체 조회
	public List<ChData> getAllChannelData() {
		return chDataRepository.findAll();
	}

	// 채널 아이디로 조회
	public List<ChData> getChannelDataByChannelId(long channelId) {
		return chDataRepository.findByChannelId(channelId);
	}

}
