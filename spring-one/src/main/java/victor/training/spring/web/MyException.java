package victor.training.spring.web;

public class MyException extends RuntimeException {

   public MyException(ErrorCode errorCode) {
      this.errorCode = errorCode;
   }

   public enum ErrorCode {
      DUPLICATE_NAME
   }

   private final ErrorCode errorCode;

   public ErrorCode getErrorCode() {
      return errorCode;
   }
}
