package com.team7.retriever.domain.drug.service;

import com.team7.retriever.domain.drug.domain.document.Drugs;
import com.team7.retriever.domain.drug.domain.repository.DrugsRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DrugsService {

	private final DrugsRepository drugsRepository;

	// 전체 조회
	public List<Drugs> getAllDrugs() {
		return drugsRepository.findAll();
	}
}
