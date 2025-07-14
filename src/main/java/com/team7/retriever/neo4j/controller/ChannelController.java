package com.team7.retriever.neo4j.controller;

import com.team7.retriever.neo4j.entity.Channel;
import com.team7.retriever.neo4j.service.ChannelService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/neo4j/channels")
@RequiredArgsConstructor
public class ChannelController {
	private final ChannelService channelService;

	@GetMapping
	public List<Channel> getAllChannels() {
		return channelService.getAllChannels();
	}

	@GetMapping("/depth")
	public List<Channel> getChannelsDepth1() {
		return channelService.getAllChannelsWithArgots();
	}
}
