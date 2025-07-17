package com.team7.retriever.domain.user.controller.dto.response;

import com.team7.retriever.domain.user.domain.document.User;
import com.team7.retriever.domain.user.domain.enums.Role;

public record UserInfoResponse(
	String employeeId,
	String name,
	Role role
) {
	public static UserInfoResponse from(User user) {
		return new UserInfoResponse(
			user.getEmployeeId(),
			user.getName(),
			user.getRole()
		);
	}
}
