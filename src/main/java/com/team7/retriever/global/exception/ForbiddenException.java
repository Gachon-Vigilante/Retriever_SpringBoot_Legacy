package com.team7.retriever.global.exception;

import com.team7.retriever.global.exception.base.BaseErrorCode;

public class ForbiddenException extends CustomException {
	public ForbiddenException(final BaseErrorCode baseErrorCode) {
		super(baseErrorCode);
	}
}
