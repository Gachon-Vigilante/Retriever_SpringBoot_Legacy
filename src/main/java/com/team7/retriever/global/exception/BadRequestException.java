package com.team7.retriever.global.exception;

import com.team7.retriever.global.exception.base.BaseErrorCode;

public class BadRequestException extends CustomException {
	public BadRequestException(final BaseErrorCode baseErrorCode) {
		super(baseErrorCode);
	}
}
