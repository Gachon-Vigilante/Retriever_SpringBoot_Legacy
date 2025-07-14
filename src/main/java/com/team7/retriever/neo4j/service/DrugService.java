package com.team7.retriever.neo4j.service;

import com.team7.retriever.neo4j.entity.Drug;
import com.team7.retriever.neo4j.repository.NeoDrugRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrugService {

	private final NeoDrugRepository neoDrugRepository;

	public List<Drug> getAllDrugs() {
		return neoDrugRepository.findAll();
	}
}
