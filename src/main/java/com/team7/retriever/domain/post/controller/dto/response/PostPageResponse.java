package com.team7.retriever.domain.post.controller.dto.response;

import java.util.List;

import com.team7.retriever.domain.post.domain.document.Posts;

import lombok.Builder;

@Builder
public record PostPageResponse(
	long totalCount,
	List<Posts> posts
) {
	public static PostPageResponse of(long totalCount, List<Posts> posts) {
		return PostPageResponse.builder()
			.totalCount(totalCount)
			.posts(posts)
			.build();
	}
}
