package com.team7.retriever.dto.request;

import com.team7.retriever.entity.enums.Role;

public record GrantRequest(
	String employeeId,
	Role role
) {
}
