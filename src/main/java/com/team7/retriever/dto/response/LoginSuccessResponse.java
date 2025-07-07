package com.team7.retriever.dto.response;

public record LoginSuccessResponse(
	String accessToken,
	String refreshToken,
	String name,
	String role
) {
	public static LoginSuccessResponse of(String accessToken, String refreshToken, String name, String role) {
		return new LoginSuccessResponse(accessToken, refreshToken, name, role);
	}
}
