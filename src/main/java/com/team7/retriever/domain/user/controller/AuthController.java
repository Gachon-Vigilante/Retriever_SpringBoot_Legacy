package com.team7.retriever.domain.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team7.retriever.global.auth.cookie.CookieProvider;
import com.team7.retriever.domain.user.controller.dto.request.LoginRequest;
import com.team7.retriever.domain.user.controller.dto.request.SignUpRequest;
import com.team7.retriever.domain.user.controller.dto.response.LoginResponse;
import com.team7.retriever.domain.user.controller.dto.response.LoginSuccessResponse;
import com.team7.retriever.domain.user.controller.dto.response.ReissueResponse;
import com.team7.retriever.domain.user.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	private final CookieProvider cookieProvider;

	@PostMapping("/signup")
	public String signUp(@RequestBody SignUpRequest signUpRequest) {
		return authService.signUp(signUpRequest);
	}

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
		LoginSuccessResponse loginSuccessResponse = authService.login(loginRequest);

		cookieProvider.setTokenCookies(httpServletResponse,
			loginSuccessResponse.accessToken(),
			loginSuccessResponse.refreshToken()
		);

		return LoginResponse.from(loginSuccessResponse);
	}

	@DeleteMapping("/logout")
	public String logout(@AuthenticationPrincipal String userId, HttpServletResponse httpServletResponse) {
		cookieProvider.deleteTokenCookies(httpServletResponse);

		return authService.logout(userId);
	}

	@PostMapping("/reissue")
	public String reissueToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		ReissueResponse reissueResponse = authService.reissueToken(httpServletRequest);

		cookieProvider.setTokenCookies(httpServletResponse,
			reissueResponse.accessToken(),
			reissueResponse.refreshToken()
		);

		return "토큰을 재발급했습니다.";
	}

	@DeleteMapping("/withdraw")
	public String withdraw(@RequestParam String loginId) {
		return authService.withdraw(loginId);
	}
}
