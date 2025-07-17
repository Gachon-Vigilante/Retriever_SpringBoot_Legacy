package com.team7.retriever.controller;

import com.team7.retriever.dto.ChatArgotDrugDTO;
import com.team7.retriever.entity.ChData;
import com.team7.retriever.service.ChDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/chat")
public class ChDataController {

    private final ChDataService chDataService;

    // 전체 채팅 조회
    @GetMapping("/all")
    public List<ChData> getAllChannelData() {
        return chDataService.getAllChannelData();
    }

    // 채널 아이디로 채팅 조회
    @GetMapping("/channel/{channelID}")
    public List<ChData> getChannelDataByChannelId(@PathVariable long channelID) {
        return chDataService.getChannelDataByChannelId(channelID);
    }
}
