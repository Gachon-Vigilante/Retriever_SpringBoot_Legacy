package com.team7.retriever.global.auth.jwt.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.team7.retriever.global.exception.NotFoundException;
import com.team7.retriever.global.auth.jwt.exception.TokenErrorCode;
import com.team7.retriever.global.auth.redis.Token;
import com.team7.retriever.global.auth.jwt.repository.TokenRepository;
import com.team7.retriever.global.exception.UnauthorizedException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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

	public String getRefreshToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			throw new UnauthorizedException(TokenErrorCode.EMPTY_OR_INVALID_TOKEN);
		}

		return Arrays.stream(cookies)
			.filter(cookie -> "refreshToken".equals(cookie.getName()))
			.map(Cookie::getValue)
			.findFirst()
			.orElseThrow(() -> new UnauthorizedException(TokenErrorCode.EMPTY_OR_INVALID_TOKEN));
	}

	public boolean existsByUserId(final String userId) {
		return tokenRepository.existsById(userId);
	}
}
