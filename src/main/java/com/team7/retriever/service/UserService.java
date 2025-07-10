package com.team7.retriever.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team7.retriever.dto.response.UserInfoResponse;
import com.team7.retriever.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public List<UserInfoResponse> getAllUsers() {
		return userRepository.findAll()
			.stream()
			.map(UserInfoResponse::from)
			.toList();
	}
}
