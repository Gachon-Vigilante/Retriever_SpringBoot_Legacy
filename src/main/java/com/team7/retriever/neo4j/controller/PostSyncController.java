package com.team7.retriever.neo4j.controller;

import com.team7.retriever.neo4j.service.PostSyncService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostSyncController {
	private final PostSyncService postSyncService;

	@GetMapping("/sync")
	public void sync() {
		postSyncService.syncPostIdsFromMongoToNeo4j();
	}
}
