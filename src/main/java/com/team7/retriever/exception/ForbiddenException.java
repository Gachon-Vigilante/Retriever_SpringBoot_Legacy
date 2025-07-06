package com.team7.retriever.exception;

import com.team7.retriever.exception.base.BaseErrorCode;

public class ForbiddenException extends CustomException {
	public ForbiddenException(final BaseErrorCode baseErrorCode) {
		super(baseErrorCode);
	}
}
