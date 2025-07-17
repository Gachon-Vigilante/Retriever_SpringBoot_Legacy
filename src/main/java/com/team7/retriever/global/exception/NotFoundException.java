package com.team7.retriever.global.exception;

import com.team7.retriever.global.exception.base.BaseErrorCode;

public class NotFoundException extends CustomException {
	public NotFoundException(final BaseErrorCode baseErrorCode) {
		super(baseErrorCode);
	}
}
