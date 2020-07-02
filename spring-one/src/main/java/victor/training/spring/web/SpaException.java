package victor.training.spring.web;

public class SpaException extends RuntimeException {
   public enum ErrorCode {
      DUPLICATE_TRAINING_NAME,
      BAD_DATE_FORMAT, GENERAL
   }

   private final ErrorCode errorCode;
   private String[] args = {};

   public SpaException(ErrorCode errorCode, String... args) {
      this.errorCode = errorCode;
      this.args = args;
   }

   public ErrorCode getErrorCode() {
      return errorCode;
   }

   public String[] getArgs() {
      return args;
   }

   public SpaException(String message, ErrorCode errorCode) {
      super(message);
      this.errorCode = errorCode;
   }
   public SpaException(String message) {
      super(message);
      this.errorCode = ErrorCode.GENERAL;
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
}
