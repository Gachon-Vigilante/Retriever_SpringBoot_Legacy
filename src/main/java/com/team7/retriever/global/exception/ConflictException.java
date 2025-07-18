package com.team7.retriever.global.exception;

import com.team7.retriever.global.exception.base.BaseErrorCode;

public class ConflictException extends CustomException {
	public ConflictException(final BaseErrorCode baseErrorCode) {
		super(baseErrorCode);
	}
}
