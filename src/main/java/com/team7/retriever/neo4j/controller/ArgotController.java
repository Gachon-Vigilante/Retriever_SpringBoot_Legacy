package com.team7.retriever.neo4j.controller;

import com.team7.retriever.neo4j.entity.Argot;
import com.team7.retriever.neo4j.service.ArgotService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/neo4j/argots")
@RequiredArgsConstructor
public class ArgotController {

	private final ArgotService argotService;

	@GetMapping
	public List<Argot> getAllArgots() {
		return argotService.getAllArgots();
	}

	@GetMapping("/depth")
	public List<Argot> getPostsDepth1() {
		return argotService.getAllArgotsWithRefersTo();
	}
}
