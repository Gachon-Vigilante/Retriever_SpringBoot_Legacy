package com.team7.retriever.domain.agrot.controller;


import com.team7.retriever.domain.agrot.domain.document.Argot;
import com.team7.retriever.domain.agrot.service.ArgotsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    // 아이디로 조회
    @GetMapping("/id/{id}") /* 250106 수정 */
    public Optional<Argot> getArgotById(@PathVariable String id) {
        return argotsService.getArgotById(id);
    }

    // argot으로 조회
    @GetMapping("/argot/{argot}") // MODI
    public List<Argot> getArgotsByArgot(@PathVariable String argot) {
        return argotsService.getArgotsByArgot(argot);
    }
}
