package com.team7.retriever.auth.jwt.service;

import org.springframework.stereotype.Service;

import com.team7.retriever.exception.NotFoundException;
import com.team7.retriever.auth.jwt.exception.TokenErrorCode;
import com.team7.retriever.auth.redis.Token;
import com.team7.retriever.auth.jwt.repository.TokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

	private final TokenRepository tokenRepository;

	@Transactional
	public void saveRefreshToken(final String userId, final String refreshToken) {
		tokenRepository.save(Token.of(userId, refreshToken));
	}

	public String findIdByRefreshToken(final String refreshToken) {
		Token token = tokenRepository.findByRefreshToken(refreshToken)
			.orElseThrow(() -> new NotFoundException(TokenErrorCode.REFRESH_TOKEN_NOT_FOUND));

		return token.getId();
	}

	@Transactional
	public void deleteRefreshToken(final String userId) {
		Token token = tokenRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException(TokenErrorCode.REFRESH_TOKEN_NOT_FOUND));

		tokenRepository.delete(token);
		log.info("refresh token을 삭제했습니다: {}", token);
	}
}
