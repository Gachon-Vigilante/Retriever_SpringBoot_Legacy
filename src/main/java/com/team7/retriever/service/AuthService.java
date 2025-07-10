package com.team7.retriever.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import com.team7.retriever.auth.jwt.exception.TokenErrorCode;
import com.team7.retriever.auth.jwt.provider.JwtTokenProvider;
import com.team7.retriever.auth.jwt.provider.JwtValidationType;
import com.team7.retriever.auth.jwt.service.TokenService;
import com.team7.retriever.auth.security.AdminAuthentication;
import com.team7.retriever.auth.security.MemberAuthentication;
import com.team7.retriever.dto.request.LoginRequest;
import com.team7.retriever.dto.request.SignUpRequest;
import com.team7.retriever.dto.response.LoginSuccessResponse;
import com.team7.retriever.dto.response.ReissueResponse;
import com.team7.retriever.entity.User;
import com.team7.retriever.entity.enums.Role;
import com.team7.retriever.exception.BadRequestException;
import com.team7.retriever.exception.CustomException;
import com.team7.retriever.exception.NotFoundException;
import com.team7.retriever.exception.UnauthorizedException;
import com.team7.retriever.exception.UserErrorCode;
import com.team7.retriever.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final TokenService tokenService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public String signUp(SignUpRequest signUpRequest) {
		String employeeId = signUpRequest.employeeId();

		if (userRepository.existsByEmployeeId(employeeId)) {
			throw new BadRequestException(UserErrorCode.ALREADY_EXISTS);
		}

		User user = User.create(employeeId, passwordEncoder.encode(signUpRequest.password()),
			signUpRequest.name(), Role.USER);

		userRepository.save(user);

		return "회원 가입이 완료되었습니다. 사용자 이름: " + user.getName();
	}

	public LoginSuccessResponse login(LoginRequest loginRequest) {
		String employeeId = loginRequest.employeeId();
		String password = loginRequest.password();

		User user = findUserWithAuthenticate(employeeId, password);

		Role role = user.getRole();
		String userId = user.getId();
		String name = user.getName();

		Collection<GrantedAuthority> authorities = List.of(role.toGrantedAuthority());

		UsernamePasswordAuthenticationToken authenticationToken = createAuthenticationToken(userId, role,
			authorities);
		String refreshToken = issueAndSaveRefreshToken(userId, authenticationToken);
		String accessToken = jwtTokenProvider.issueAccessToken(authenticationToken);

		log.info("로그인에 성공하였습니다. authorities: {}, accessToken: {}, refreshToken: {}", authorities, accessToken,
			refreshToken);

		return LoginSuccessResponse.of(accessToken, refreshToken, name, role.getRoleName());
	}

	@Transactional
	public ReissueResponse reissueToken(HttpServletRequest httpServletRequest) {
		String refreshToken = tokenService.getRefreshToken(httpServletRequest);

		validateRefreshToken(refreshToken);

		String userId = jwtTokenProvider.getUserIdFromJwt(refreshToken);
		verifyUserIdWithStoredToken(refreshToken, userId);

		Role role = jwtTokenProvider.getRoleFromJwt(refreshToken);
		Collection<GrantedAuthority> authorities = List.of(role.toGrantedAuthority());

		UsernamePasswordAuthenticationToken authenticationToken = createAuthenticationToken(userId, role,
			authorities);
		log.info("새로운 access token을 생성하였습니다. userId: {}, role: {}, authorities: {}",
			userId, role.getRoleName(), authorities);

		return ReissueResponse.of(jwtTokenProvider.issueAccessToken(authenticationToken),
			jwtTokenProvider.issueRefreshToken(authenticationToken));
	}

	@Transactional
	public String logout(String userId) {
		tokenService.deleteRefreshToken(userId);

		return "로그아웃이 완료되었습니다.";
	}

	@Transactional
	public String withdraw(String userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException(UserErrorCode.NOT_FOUND));

		userRepository.delete(user);
		tokenService.deleteRefreshToken(userId);

		log.info("회원 탈퇴가 완료되었습니다. userId: {}", userId);
		return "회원 탈퇴가 완료되었습니다.";
	}

	private User findUserWithAuthenticate(String employeeId, String password) {
		User user = userRepository.findByEmployeeId(employeeId)
			.orElseThrow(() -> new NotFoundException(UserErrorCode.NOT_FOUND));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new UnauthorizedException(UserErrorCode.PASSWORD_MISMATCH);
		}

		return user;
	}

	private String issueAndSaveRefreshToken(String userId, UsernamePasswordAuthenticationToken authenticationToken) {
		String refreshToken = jwtTokenProvider.issueRefreshToken(authenticationToken);
		log.info("새로운 refresh token이 발급되었습니다. userId: {}", userId);
		tokenService.saveRefreshToken(userId, refreshToken);
		return refreshToken;
	}

	private UsernamePasswordAuthenticationToken createAuthenticationToken(String userId, Role role,
		Collection<GrantedAuthority> authorities) {
		if (role == Role.USER) {
			log.info("Creating MemberAuthentication for userId: {}", userId);
			return new MemberAuthentication(userId, null, authorities);
		} else {
			log.info("Creating AdminAuthentication for userId: {}", userId);
			return new AdminAuthentication(userId, null, authorities);
		}
	}

	private void validateRefreshToken(String refreshToken) {
		JwtValidationType validationType = jwtTokenProvider.validateToken(refreshToken);

		if (!validationType.equals(JwtValidationType.VALID_JWT)) {
			throw switch (validationType) {
				case EXPIRED_JWT_TOKEN -> new UnauthorizedException(TokenErrorCode.REFRESH_TOKEN_EXPIRED_ERROR);
				case INVALID_JWT_TOKEN -> new BadRequestException(TokenErrorCode.INVALID_REFRESH_TOKEN_ERROR);
				case INVALID_JWT_SIGNATURE -> new BadRequestException(TokenErrorCode.REFRESH_TOKEN_SIGNATURE_ERROR);
				case UNSUPPORTED_JWT_TOKEN -> new BadRequestException(TokenErrorCode.UNSUPPORTED_REFRESH_TOKEN_ERROR);
				case EMPTY_JWT -> new BadRequestException(TokenErrorCode.REFRESH_TOKEN_EMPTY_ERROR);
				default -> new CustomException(TokenErrorCode.UNKNOWN_REFRESH_TOKEN_ERROR);
			};
		}
	}

	private void verifyUserIdWithStoredToken(String refreshToken, String userId) {
		String storedUserId = tokenService.findIdByRefreshToken(refreshToken);

		if (!userId.equals(storedUserId)) {
			throw new BadRequestException(TokenErrorCode.REFRESH_TOKEN_USER_ID_MISMATCH_ERROR);
		}
	}
}

