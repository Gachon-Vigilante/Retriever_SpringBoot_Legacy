package com.team7.retriever.domain.user.controller.dto.request;

public record SignUpRequest(
	String employeeId,
	String password,
	String name
) {
}
