package com.example.demo;

public class DemoException extends RuntimeException {

    public enum ErrorCode {
        GENERAL,
        DUPLICATE_NAME
    }

    private final ErrorCode errorCode;
//    private final Object[] params;

    public DemoException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public DemoException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DemoException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public DemoException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public DemoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
}
