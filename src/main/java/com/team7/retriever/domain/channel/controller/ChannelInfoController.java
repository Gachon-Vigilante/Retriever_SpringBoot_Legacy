package com.team7.retriever.domain.channel.controller;

import com.team7.retriever.domain.channel.controller.dto.request.ChannelInfoRequest;
import com.team7.retriever.service.ChannelInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/channelInfo")
public class ChannelInfoController {
    private final ChannelInfoService channelInfoService;

    @GetMapping
    public void getChannelInfo(@RequestBody ChannelInfoRequest request) {
        channelInfoService.getChannelInfo(request.getChannelKey(), request.getPostId());
    }
}
