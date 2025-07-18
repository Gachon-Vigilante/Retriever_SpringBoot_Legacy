package com.team7.retriever.global.exception;


import com.team7.retriever.global.exception.base.BaseErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

	ALREADY_GRANTED_ROLE(400, "이미 해당 역할이 부여된 사용자입니다"),
	ALREADY_EXISTS(400, "이미 존재하는 사용자입니다"),
	CAN_NOT_CHANGE_MY_ROLE(400, "본인의 역할은 변경할 수 없습니다"),
	CAN_NOT_GRANT_ROOT_ROLE(400, "Root 역할은 부여할 수 없습니다"),
	PASSWORD_MISMATCH(400, "비밀번호가 일치하지 않습니다"),
	NOT_FOUND(404, "사용자를 찾을 수 없습니다");

	private final int status;
	private final String message;
}
