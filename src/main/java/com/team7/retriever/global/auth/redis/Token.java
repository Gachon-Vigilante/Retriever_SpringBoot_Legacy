package com.team7.retriever.global.auth.redis;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@RedisHash(value = "refreshToken", timeToLive = 1209600)
public class Token {

	@Id
	private String id;

	@Indexed
	private String refreshToken;

	public static Token of(final String id, final String refreshToken) {
		return Token.builder()
			.id(id)
			.refreshToken(refreshToken)
			.build();
	}
}
