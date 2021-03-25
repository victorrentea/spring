package victor.training.spring.web;

public class MyException extends RuntimeException{

   private final ErrorCode code;

   public MyException(ErrorCode code) {
      this.code = code;
   }

   public enum ErrorCode {
      DUPLICATED_NAME
   }

   public ErrorCode getCode() {
      return code;
   }
}
