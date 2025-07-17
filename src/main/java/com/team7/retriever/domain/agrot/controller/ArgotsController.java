package com.team7.retriever.domain.agrot.controller;


import com.team7.retriever.domain.agrot.domain.document.Argot;
import com.team7.retriever.service.ArgotsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/argots") // MODI
public class ArgotsController {

    private final ArgotsService argotsService;

    // 전체 조회
    @GetMapping("/all")
    public List<Argot> getAllArgots() {
        return argotsService.getAllArgots();
    }

    // argot으로 조회
    @GetMapping("/argot/{argot}") // MODI
    public List<Argot> getArgotsByArgot(@PathVariable String argot) {
        return argotsService.getArgotsByArgot(argot);
    }
}
