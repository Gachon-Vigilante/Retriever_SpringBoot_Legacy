package com.team7.retriever.global.exception;

import com.team7.retriever.global.exception.base.BaseErrorCode;

public class UnauthorizedException extends CustomException {
	public UnauthorizedException(final BaseErrorCode baseErrorCode) {
		super(baseErrorCode);
	}
}
