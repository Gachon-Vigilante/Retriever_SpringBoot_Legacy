package com.team7.retriever.service;

import com.team7.retriever.domain.chatbot.domain.document.ChatBots;
import com.team7.retriever.repository.ChatBotsRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatBotsService {

	private final ChatBotsRepository chatBotsRepository;

	// all
	public List<ChatBots> getAllChatBots() {
		return chatBotsRepository.findAll();
	}

	// id
	public Optional<ChatBots> getChatBotsById(long id) {
		return chatBotsRepository.findById(id);
	}

	// channels
	public List<ChatBots> getChatBotsByChannels(List<Long> channelIds) {
		return chatBotsRepository.findByChannelsIn(channelIds);
	}
}
