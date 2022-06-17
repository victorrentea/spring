package victor.training.spring.web;

public class MyException extends RuntimeException {

    public enum ErrorCode {
        DUPLICATED_NAME,
        GENERAL
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    private final ErrorCode errorCode;

    public MyException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public MyException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MyException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public MyException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
//    private String[] args;

}
