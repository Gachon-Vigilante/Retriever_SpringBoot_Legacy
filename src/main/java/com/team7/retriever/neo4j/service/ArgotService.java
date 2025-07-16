package com.team7.retriever.neo4j.service;

import com.team7.retriever.neo4j.entity.Argot;
import com.team7.retriever.neo4j.repository.NeoArgotRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArgotService {

	private final NeoArgotRepository neoArgotRepository;

	public List<Argot> getAllArgots() {
		return neoArgotRepository.findAll();
	}

	public List<Argot> getAllArgotsWithRefersTo() {
		return neoArgotRepository.findAllWithRefersTo();
	}
}
