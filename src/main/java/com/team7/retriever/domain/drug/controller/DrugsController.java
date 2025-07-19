package com.team7.retriever.domain.drug.controller;


import com.team7.retriever.domain.drug.domain.document.Drugs;
import com.team7.retriever.domain.drug.service.DrugsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/drugs")
public class DrugsController {

    private final DrugsService drugsService;

    // 아이디로 조회
    @GetMapping("/id/{id}") /* 241231 수정 */
    public Optional<Drugs> getDrugById(@PathVariable String id) {
        return drugsService.getDrugById(id);
    }

    // 전체 조회
    @GetMapping("/all")
    public List<Drugs> getAllDrugs() {
        return drugsService.getAllDrugs();
    }
}
