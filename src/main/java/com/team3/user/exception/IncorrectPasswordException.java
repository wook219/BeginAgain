package com.team3.user.exception;

import com.team3.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class IncorrectPasswordException extends RuntimeException {
    private final ErrorCode errorCode;

    public IncorrectPasswordException() {
        super(ErrorCode.INVALID_PASSWORD.getMessage());
        this.errorCode = ErrorCode.INVALID_PASSWORD;
    }
}
