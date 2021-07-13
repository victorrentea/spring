package victor.training.spring.web;

public class MyException extends RuntimeException {
   public MyException(ErrorCode code) {
      this.code = code;
   }

   public enum ErrorCode {
      GENERAL,
      DUPLICATE_COURSE_NAME
   }
   private final ErrorCode code;
//   private Object[] args;

   public ErrorCode getCode() {
      return code;
   }
}
