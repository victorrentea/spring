package victor.training.spring;

public class LocalizableException extends RuntimeException {
   public enum ErrorCode {
      DUPLICATED_NAME

   }

   public String[] getArgs() {
      return args;
   }

   private final ErrorCode errorCode;
   private final String[] args;
//   private final int httpStatusCode;

   public LocalizableException(ErrorCode errorCode, String... args) {
      this.errorCode = errorCode;
      this.args = args;
   }

   public ErrorCode getErrorCode() {
      return errorCode;
   }

}
