package com.team7.retriever.domain.user.controller.dto.request;

public record LoginRequest(
	String employeeId,
	String password
) {
}
