package com.team7.retriever.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.team7.retriever.entity.enums.Role;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Document(collection = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	private String id;

	private String employeeId;

	private String password;

	private String name;

	private Role role;

	private boolean active;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	public static User create(String employeeId, String password, String name, Role role) {
		return User.builder()
			.employeeId(employeeId)
			.password(password)
			.name(name)
			.role(role)
			.active(false)
			.build();
	}

	public void grantRole(Role role) {
		this.role = role;
	}
}
