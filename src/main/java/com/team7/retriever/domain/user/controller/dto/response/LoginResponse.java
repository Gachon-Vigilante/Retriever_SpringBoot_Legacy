package com.team7.retriever.domain.user.controller.dto.response;

public record LoginResponse(
	String name,
	String role
) {
	public static LoginResponse from(LoginSuccessResponse loginSuccessResponse) {
		return new LoginResponse(loginSuccessResponse.name(), loginSuccessResponse.role());
	}
}
