package com.team7.retriever.dto.response;

public record LoginResponse(
	String name,
	String role
) {
	public static LoginResponse from(LoginSuccessResponse loginSuccessResponse) {
		return new LoginResponse(loginSuccessResponse.name(), loginSuccessResponse.role());
	}
}
