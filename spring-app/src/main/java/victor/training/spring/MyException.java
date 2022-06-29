package victor.training.spring;

public class MyException extends RuntimeException{
    public enum ErrorCode {
        DUPLICATED_TRAINING_NAME,
        NOT_FOUND, GENERAL
    }

    public ErrorCode getErrorCode() {
            return errorCode;
    }

    public String[] getParams() {
        return params;
    }

    private final ErrorCode errorCode;
    private final String[] params;


    public MyException(ErrorCode errorCode, String... params) {
        this.errorCode = errorCode;
        this.params = params;
    }

    public MyException(String message, ErrorCode errorCode, String[] params) {
        super(message);
        this.errorCode = errorCode;
        this.params = params;
    }

    public MyException(String message, Throwable cause, ErrorCode errorCode, String[] params) {
        super(message, cause);
        this.errorCode = errorCode;
        this.params = params;
    }

    public MyException(Throwable cause, ErrorCode errorCode, String[] params) {
        super(cause);
        this.errorCode = errorCode;
        this.params = params;
    }

    public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode, String[] params) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.params = params;
    }
}
