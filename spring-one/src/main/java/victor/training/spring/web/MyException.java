package victor.training.spring.web;

public class MyException extends RuntimeException {

   public MyException(ErrorCode code) {
      this.code = code;
   }

   public enum ErrorCode {
      TRAINING_DUPLICATE_NAME
   }
   private final ErrorCode code;


   public ErrorCode getCode() {
      return code;
   }
}
