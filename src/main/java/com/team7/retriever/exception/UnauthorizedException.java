package com.team7.retriever.exception;

import com.team7.retriever.exception.base.BaseErrorCode;

public class UnauthorizedException extends CustomException {
	public UnauthorizedException(final BaseErrorCode baseErrorCode) {
		super(baseErrorCode);
	}
}
