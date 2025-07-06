package com.team7.retriever.exception;

import com.team7.retriever.exception.base.BaseErrorCode;

public class BadRequestException extends CustomException {
	public BadRequestException(final BaseErrorCode baseErrorCode) {
		super(baseErrorCode);
	}
}
