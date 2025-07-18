package com.team7.retriever.domain.bookmark.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookmarksRequest {
	private String channelId;
	private String userId;

}
