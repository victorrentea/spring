package victor.training.spring.spa;

public class SpaException extends RuntimeException {

    public enum ErrorCode {
        MISSING_NAME("error.missing.name"),
        DUPLICATE_NAME("error.duplicate.name"),
        INVALID_DATE("error.invalid.date"),
        GENERAL("error.general");
        public final String messageKey;

        ErrorCode(String messageKey) {
            this.messageKey = messageKey;
        }
    }
    private final ErrorCode errorCode;
    private final String[] args;

    public SpaException(ErrorCode errorCode) {
        this(null, null, errorCode);

    }
    public SpaException(ErrorCode errorCode, String... args) {
        this(null, null, errorCode, args);
    }

    public SpaException(String message, ErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public SpaException(String message, Throwable cause, ErrorCode errorCode, String... args) {
        super(message, cause);
        this.errorCode = errorCode;
        this.args = args;
    }

    public SpaException(Throwable cause, ErrorCode errorCode) {
        this(null, cause, errorCode);
    }


    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String[] getArgs() {
        return args;
    }
}
