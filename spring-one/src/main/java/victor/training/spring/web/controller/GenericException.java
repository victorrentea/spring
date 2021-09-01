package victor.training.spring.web.controller;

public class GenericException extends RuntimeException{

   public GenericException(ErrorCode code) {
      this.code = code;
   }

   public enum ErrorCode {
      DUPLICATED_TRAINING(404/*, "User-facing eerorr message"*/);

      private final int httpCode;

      ErrorCode(int httpCode) {
         this.httpCode = httpCode;
      }

      public int getHttpCode() {
         return httpCode;
      }
   }
   private final ErrorCode code;

   public ErrorCode getCode() {
      return code;
   }
}
