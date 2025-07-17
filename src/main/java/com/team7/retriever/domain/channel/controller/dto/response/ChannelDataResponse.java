package com.team7.retriever.domain.channel.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChannelDataResponse {
	private String message;
	private String status;
}
