package com.team7.retriever.global.auth.jwt.provider;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.team7.retriever.domain.user.domain.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.access-token-expire-time}")
	private long accessTokenExpireTime;

	@Value("${jwt.refresh-token-expire-time}")
	private long refreshTokenExpireTime;

	private static final String USER_ID = "userId";
	private static final String ROLE_KEY = "role";

	@PostConstruct
	protected void init() {
		jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}

	public String issueAccessToken(final Authentication authentication) {
		return issueToken(authentication, accessTokenExpireTime);
	}

	public String issueRefreshToken(final Authentication authentication) {
		return issueToken(authentication, refreshTokenExpireTime);
	}

	public JwtValidationType validateToken(String token) {
		try {
			Claims claims = getBody(token);
			return JwtValidationType.VALID_JWT;
		} catch (MalformedJwtException ex) {
			log.error("올바르지 않은 JWT 토큰입니다: {}", ex.getMessage());
			return JwtValidationType.INVALID_JWT_TOKEN;
		} catch (ExpiredJwtException ex) {
			log.error("만료된 JWT 토큰입니다: {}", ex.getMessage());
			return JwtValidationType.EXPIRED_JWT_TOKEN;
		} catch (UnsupportedJwtException ex) {
			log.error("지원하지 않는 JWT 토큰입니다: {}", ex.getMessage());
			return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
		} catch (IllegalArgumentException ex) {
			log.error("JWT 토큰이 비어있거나 올바르지 않은 인자입니다: {}", ex.getMessage());
			return JwtValidationType.EMPTY_JWT;
		} catch (SignatureException ex) {
			log.error("올바르지 않은 JWT Signature 입니다: {}", ex.getMessage());
			return JwtValidationType.INVALID_JWT_SIGNATURE;
		}
	}

	public String getUserIdFromJwt(String token) {
		Claims claims = getBody(token);
		String userId = claims.get(USER_ID).toString();

		// 로그 추가: userId 확인
		log.info("Extracted userId from JWT: {}", userId);

		return userId;
	}

	public Role getRoleFromJwt(String token) {
		Claims claims = getBody(token);
		String roleName = claims.get(ROLE_KEY, String.class);

		log.info("JWT에서 추출된 role: {}", roleName);

		// "ROLE_" 접두사 제거
		String enumValue = roleName.replace("ROLE_", "");
		log.info("사용자 role: {}", enumValue);

		return Role.valueOf(enumValue.toUpperCase());
	}

	private String issueToken(final Authentication authentication, final long expiredTime) {
		final Date now = new Date();

		final Claims claims = Jwts.claims().setIssuedAt(now).setExpiration(new Date(now.getTime() + expiredTime));

		claims.put(USER_ID, authentication.getPrincipal());
		log.info("claims에 user id를 추가했습니다: {}", authentication.getPrincipal());
		log.info("토큰 생성 전 Authorities: {}", authentication.getAuthorities());

		String role = authentication.getAuthorities()
			.stream()
			.map(GrantedAuthority::getAuthority)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("No authorities found for user"));

		log.info("Selected role for token: {}", role);

		claims.put(ROLE_KEY, role);
		log.info("claims에 role을 추가했습니다: {}", role);

		return Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setClaims(claims)
			.signWith(getSigningKey())
			.compact();
	}

	private Claims getBody(final String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	private SecretKey getSigningKey() {
		String encodedKey = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
		return Keys.hmacShaKeyFor(encodedKey.getBytes());
	}
}
