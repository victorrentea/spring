package victor.training.spring.web;

public class MyException extends RuntimeException {
   public MyException(ErrorCode errorCode) {
      this.errorCode = errorCode;
   }

   public MyException(String message, ErrorCode errorCode) {
      super(message);
      this.errorCode = errorCode;
   }

   public ErrorCode getErrorCode() {
      return errorCode;
   }

   public MyException(String message, Throwable cause, ErrorCode errorCode) {
      super(message, cause);
      this.errorCode = errorCode;
   }

   public MyException(Throwable cause, ErrorCode errorCode) {
      super(cause);
      this.errorCode = errorCode;
   }

   public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
      super(message, cause, enableSuppression, writableStackTrace);
      this.errorCode = errorCode;
   }

   public enum ErrorCode {
      DUPLICATED_TRAINING_NAME("Another training with that name already exists");

      private final String userMessage;

      ErrorCode(String userMessage) {
         this.userMessage = userMessage;
      }

      public String getUserMessage() {
         return userMessage;
      }
   }

   private final ErrorCode errorCode;
}
