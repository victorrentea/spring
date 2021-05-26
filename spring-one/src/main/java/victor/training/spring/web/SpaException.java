package victor.training.spring.web;

public class SpaException extends RuntimeException {
   public SpaException(ErrorCode code) {
      this.code = code;
   }

   public enum ErrorCode {
      DUPLICATE_NAME
   }
   private final ErrorCode code;

   public ErrorCode getCode() {
      return code;
   }
}
