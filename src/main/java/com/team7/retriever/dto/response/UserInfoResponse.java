package com.team7.retriever.dto.response;

import com.team7.retriever.entity.User;
import com.team7.retriever.entity.enums.Role;

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
