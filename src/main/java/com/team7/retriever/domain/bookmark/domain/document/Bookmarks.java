package com.team7.retriever.domain.bookmark.domain.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "bookmarks")
public class Bookmarks {

	@Id
	private String id;
	private String channelId;
	private String userId;
	private LocalDateTime createdAt;

	@Override
	public String toString() {
		return "Bookmark{" +
			"id='" + id + '\'' +
			", channelId='" + channelId + '\'' +
			", userId='" + userId + '\'' +
			", createdAt=" + createdAt +
			'}';
	}

	@Builder
	private Bookmarks(String channelId, String userId) {
		this.channelId = channelId;
		this.userId = userId;
		this.createdAt = LocalDateTime.now();  // 현재 시간으로 설정
		this.id = "bm_" + userId + "_" + channelId;  // ID 생성 규칙에 따라 설정
	}

	public static Bookmarks create(String channelId, String userId) {
		return Bookmarks.builder()
			.channelId(channelId)
			.userId(userId)
			.build();
	}
}
