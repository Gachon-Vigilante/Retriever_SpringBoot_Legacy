package com.team7.retriever.neo4j.service;

import com.team7.retriever.neo4j.entity.Channel;
import com.team7.retriever.neo4j.repository.NeoChannelRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelService {
	private final NeoChannelRepository channelRepository;

	public List<Channel> getAllChannels() {
		Iterable<Channel> iterable = channelRepository.findAll();
		List<Channel> list = new ArrayList<>();
		iterable.forEach(list::add);
		return list;
	}

	public List<Channel> getAllChannelsWithArgots() {
		return channelRepository.findAllWithSells();
	}
}
