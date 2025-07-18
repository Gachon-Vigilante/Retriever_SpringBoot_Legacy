package com.team7.retriever.global.exception;


import com.team7.retriever.global.exception.base.BaseErrorCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final BaseErrorCode baseErrorCode;

	public CustomException(BaseErrorCode baseErrorCode) {
		super(baseErrorCode.getMessage());
		this.baseErrorCode = baseErrorCode;
	}
}
