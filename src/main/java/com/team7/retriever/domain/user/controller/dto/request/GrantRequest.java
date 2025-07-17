package com.team7.retriever.domain.user.controller.dto.request;

import com.team7.retriever.domain.user.domain.enums.Role;

public record GrantRequest(
	String employeeId,
	Role role
) {
}
