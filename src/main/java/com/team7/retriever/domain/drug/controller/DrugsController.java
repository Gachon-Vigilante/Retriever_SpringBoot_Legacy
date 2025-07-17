package com.team7.retriever.domain.drug.controller;


import com.team7.retriever.entity.Drugs;
import com.team7.retriever.service.DrugsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/drugs")
public class DrugsController {

    private final DrugsService drugsService;

    // 전체 조회
    @GetMapping("/all")
    public List<Drugs> getAllDrugs() {
        return drugsService.getAllDrugs();
    }
}
