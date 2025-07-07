package com.team7.retriever.exception;

import com.team7.retriever.exception.base.BaseErrorCode;

public class NotFoundException extends CustomException {
	public NotFoundException(final BaseErrorCode baseErrorCode) {
		super(baseErrorCode);
	}
}
