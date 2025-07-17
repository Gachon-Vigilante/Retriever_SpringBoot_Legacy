package com.team7.retriever.service;

import com.team7.retriever.entity.Reports;
import com.team7.retriever.repository.ReportsRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportsService {

	private final ReportsRepository reportsRepository;

	// 전체 조회
	public List<Reports> getAllReports() {
		return reportsRepository.findAll();
	}

	// 아이디로 조회
	public Optional<Reports> getReportById(String id) {
		return reportsRepository.findById(id);
	}

	// 채널 아이디로 조회
	public List<Reports> getReportByChId(long chId) {
		return reportsRepository.findByChannelId(chId);
	}

}
