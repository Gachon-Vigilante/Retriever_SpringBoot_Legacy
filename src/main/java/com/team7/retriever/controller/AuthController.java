package com.team7.retriever.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team7.retriever.auth.cookie.CookieProvider;
import com.team7.retriever.dto.request.LoginRequest;
import com.team7.retriever.dto.request.SignUpRequest;
import com.team7.retriever.dto.response.LoginResponse;
import com.team7.retriever.dto.response.LoginSuccessResponse;
import com.team7.retriever.dto.response.ReissueResponse;
import com.team7.retriever.service.AuthService;

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
	public String logout(@CookieValue String refreshToken, HttpServletResponse httpServletResponse) {
		cookieProvider.deleteTokenCookies(httpServletResponse);

		return authService.logout(refreshToken);
	}

	@PostMapping("/reissue")
	public String reissueToken(@CookieValue String refreshToken, HttpServletResponse httpServletResponse) {
		ReissueResponse reissueResponse = authService.reissueToken(refreshToken);

		cookieProvider.setTokenCookies(httpServletResponse,
			reissueResponse.accessToken(),
			reissueResponse.refreshToken()
		);

		return "토큰을 재발급했습니다.";
	}

	@PatchMapping("/grant-user")
	public String grantUserRole(@AuthenticationPrincipal String id, @RequestBody String employeeId) {
		return authService.grantUserRole(id, employeeId);
	}

	@PatchMapping("/grant-admin")
	public String grantAdminRole(@AuthenticationPrincipal String id, @RequestBody String employeeId) {
		return authService.grantAdminRole(id, employeeId);
	}

	@PatchMapping("/grant-guest")
	public String grantGuestRole(@AuthenticationPrincipal String id, @RequestBody String employeeId) {
		return authService.grantGuestRole(id, employeeId);
	}
}
