package com.team7.retriever.neo4j.controller;

import com.team7.retriever.neo4j.entity.Drug;
import com.team7.retriever.neo4j.service.DrugService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/neo4j/drugs")
@RequiredArgsConstructor
public class DrugController {

	private final DrugService drugService;

	@GetMapping
	public List<Drug> getAllDrugs() {
		return drugService.getAllDrugs();
	}
}