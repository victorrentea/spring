package victor.training.spring.web;

public class TheOneException extends RuntimeException {

   public enum ErrorCode {
      GENERAL,
      DUPLICATE_TRAINING_NAME,
      NOT_FOUND
   }

   private final ErrorCode code;
   private final Object[] params;

   public ErrorCode getCode() {
      return code;
   }

   public Object[] getParams() {
      return params;
   }

   public TheOneException(Exception e) {
      this(e, ErrorCode.GENERAL);
   }

   public TheOneException(String message, Exception cause) {
      this(message, cause, ErrorCode.GENERAL);
   }

   public TheOneException(ErrorCode code, Object... params) {
      this.code = code;
      this.params = params;
   }

   public TheOneException(String message, ErrorCode code, Object... params) {
      super(message);
      this.code = code;
      this.params = params;
   }

   public TheOneException(String message, Throwable cause, ErrorCode code, Object... params) {
      super(message, cause);
      this.code = code;
      this.params = params;
   }

   public TheOneException(Throwable cause, ErrorCode code, Object... params) {
      super(cause);
      this.code = code;
      this.params = params;
   }

   public TheOneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode code, Object... params) {
      super(message, cause, enableSuppression, writableStackTrace);
      this.code = code;
      this.params = params;
   }
}
