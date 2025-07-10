package com.team7.retriever.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team7.retriever.dto.response.UserInfoResponse;
import com.team7.retriever.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@GetMapping
	public List<UserInfoResponse> getUsers() {
		return userService.getAllUsers();
	}
}
