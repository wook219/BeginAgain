package com.team3.user.exception;

import com.team3.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordExistsException extends RuntimeException {
    private final ErrorCode errorCode;
    // 비밀번호 중복
    public PasswordExistsException() {
        super(ErrorCode.PASSWORD_ALREADY_EXISTS.getMessage());
        this.errorCode = ErrorCode.PASSWORD_ALREADY_EXISTS;
    }
}