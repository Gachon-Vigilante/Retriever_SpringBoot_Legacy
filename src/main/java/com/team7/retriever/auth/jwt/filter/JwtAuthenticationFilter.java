package com.team7.retriever.auth.jwt.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.team7.retriever.auth.jwt.exception.TokenErrorCode;
import com.team7.retriever.auth.security.AdminAuthentication;
import com.team7.retriever.auth.security.MemberAuthentication;
import com.team7.retriever.entity.enums.Role;
import com.team7.retriever.auth.jwt.provider.JwtTokenProvider;
import com.team7.retriever.auth.jwt.provider.JwtValidationType;
import com.team7.retriever.exception.UnauthorizedException;
import com.team7.retriever.exception.UserErrorCode;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws ServletException, IOException {

		final String token = getJwtFromRequest(request);

		if (!StringUtils.hasText(token)) {
			log.debug("쿠키에서 JWT 토큰을 찾을 수 없습니다.");
			filterChain.doFilter(request, response);
			return;
		}

		try {
			JwtValidationType validationType = jwtTokenProvider.validateToken(token);

			if (validationType == JwtValidationType.VALID_JWT) {
				setAuthentication(token, request);
				filterChain.doFilter(request, response);
			} else {
				handleInvalidToken(validationType, response);
			}
		} catch (Exception e) {
			log.error("JWT Authentication Exception: ", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 응답
		}
	}

	private void setAuthentication(String token, HttpServletRequest request) {
		String userId = jwtTokenProvider.getUserIdFromJwt(token);
		Role role = jwtTokenProvider.getRoleFromJwt(token);

		log.info("Setting authentication for userId: {} with role: {}", userId, role);

		Collection<GrantedAuthority> authorities = List.of(role.toGrantedAuthority());
		UsernamePasswordAuthenticationToken authentication = createAuthentication(userId, authorities, role);
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		log.info("Authentication set: userId: {}, role: {}", userId, role);

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private void handleInvalidToken(JwtValidationType validationType, HttpServletResponse response) {
		if (validationType == JwtValidationType.EXPIRED_JWT_TOKEN) {
			log.warn("JWT 토큰이 만료되었습니다.");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 응답
		} else {
			log.warn("올바르지 않은 JWT 토큰입니다.");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 응답
		}
	}

	private UsernamePasswordAuthenticationToken createAuthentication(String userId,
		Collection<GrantedAuthority> authorities, Role role) {
		log.info("Creating authentication for userId: {} with role: {}", userId, role);

		if (role == Role.ADMIN || role == Role.ROOT) {
			log.info("Creating AdminAuthentication for userId: {}", userId);
			return new AdminAuthentication(userId, null, authorities);
		} else if (role == Role.USER) {
			log.info("Creating MemberAuthentication for userId: {}", userId);
			return new MemberAuthentication(userId, null, authorities);
		} else if (role == Role.GUEST) {
			log.info("Creating GuestAuthentication for userId: {}", userId);
			throw new UnauthorizedException(UserErrorCode.UNAUTHORIZED);
		}
		log.error("알 수 없는 role 입니다: {}", role);
		throw new IllegalArgumentException("알 수 없는 role 입니다: " + role);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		try {
			return
				Arrays.stream(request.getCookies())
					.filter(cookie -> "accessToken".equals(cookie.getName()))
					.map(Cookie::getValue)
					.findFirst()
					.orElseThrow(() -> new UnauthorizedException(TokenErrorCode.EMPTY_OR_INVALID_TOKEN));
		} catch (Exception e) {
			log.warn("AccessToken 추출 실패: {}", e.getMessage());
			return null;
		}
	}
}
