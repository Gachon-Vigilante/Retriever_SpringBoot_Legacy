package com.team7.retriever.service;

import com.team7.retriever.entity.Argot;
import com.team7.retriever.repository.ArgotsRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArgotsService {

	private final ArgotsRepository argotsRepository;

	// 전체 조회
	public List<Argot> getAllArgots() {
		return argotsRepository.findAll();
	}

	// argot으로 조회 ; slang -> argot (name) 으로 변경
	public List<Argot> getArgotsByArgot(String argot) {
		return argotsRepository.findByArgot(argot);
	}

	// DB에서 slang 리스트로 받아오기 (t.me)
	public List<String> getAllArgotsToListWtme() {
		return argotsRepository.findAll() // DB에서 전체 조회
			.stream()
			.map(Argot::getArgot) // argot 필드만 추출
			.filter(argot -> argot != null) // argot이 null이 아닌 경우만
			.map(argot -> "t.me " + argot) // 각 argot 앞에 t.me 추가
			.toList(); // 리스트로 변환
	}

	// DB에서 slang 리스트로 받아오기
	public List<String> getAllArgotsToList() {
		return argotsRepository.findAll()
			.stream()
			.map(Argot::getArgot)
			.filter(argot -> argot != null)
			.toList();
	}
}
