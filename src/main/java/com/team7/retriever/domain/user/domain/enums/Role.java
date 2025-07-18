package com.team7.retriever.domain.user.domain.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {

	ROOT("ROLE_ROOT"),
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");

	private final String roleName;

	public GrantedAuthority toGrantedAuthority() {
		return new SimpleGrantedAuthority(roleName);
	}
}
