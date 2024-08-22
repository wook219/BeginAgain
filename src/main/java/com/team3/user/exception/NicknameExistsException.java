package com.team3.user.exception;

import com.team3.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NicknameExistsException extends RuntimeException {
    private final ErrorCode errorCode;

    public NicknameExistsException() {
        super(ErrorCode.NICKNAME_ALREADY_EXISTS.getMessage());
        this.errorCode = ErrorCode.NICKNAME_ALREADY_EXISTS;
    }
}