package com.team7.retriever.controller;


import com.team7.retriever.entity.Drugs;
import com.team7.retriever.service.DrugsService;
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

    // 전체 조회
    @GetMapping("/all")
    public List<Drugs> getAllDrugs() {
        return drugsService.getAllDrugs();
    }
}
