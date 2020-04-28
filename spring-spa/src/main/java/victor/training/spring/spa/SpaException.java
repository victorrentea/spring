package victor.training.spring.spa;

public class SpaException extends RuntimeException {

    public enum ErrorCode {
        MISSING_NAME("error.missing.name"),
        DUPLICATE_NAME("error.duplicate.name"),
        GENERAL("error.general");
        public final String messageKey;

        ErrorCode(String messageKey) {
            this.messageKey = messageKey;
        }
    }
    private final ErrorCode errorCode;

    public SpaException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public SpaException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public SpaException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public SpaException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public SpaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
