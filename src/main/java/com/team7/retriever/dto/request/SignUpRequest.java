package com.team7.retriever.dto.request;

public record SignUpRequest(
	String employeeId,
	String password,
	String name
) {
}
