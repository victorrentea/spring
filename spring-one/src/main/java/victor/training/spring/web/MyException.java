package victor.training.spring.web;

public class MyException extends RuntimeException {
   public MyException(ErrorCode errorCode, Object... args) {
      this.errorCode = errorCode;
      this.args = args;
   }

   public ErrorCode getErrorCode() {
      return errorCode;
   }

   public Object[] getArgs() {
      return args;
   }

   public enum ErrorCode {
      GENERAL,
      DUPLICATED_TRAINING_NAME
   }
   private final ErrorCode errorCode;
   private final Object[] args;
}