package com.team3.user.exception;

import com.team3.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EmailNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public EmailNotFoundException() {
        super(ErrorCode.EMAIL_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.EMAIL_NOT_FOUND;
    }
}
