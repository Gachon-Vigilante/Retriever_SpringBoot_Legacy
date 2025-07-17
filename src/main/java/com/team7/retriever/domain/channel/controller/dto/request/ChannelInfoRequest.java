package com.team7.retriever.domain.channel.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChannelInfoRequest {
	@JsonProperty("channel_key")
	private String channelKey;
	private String postId;
}
