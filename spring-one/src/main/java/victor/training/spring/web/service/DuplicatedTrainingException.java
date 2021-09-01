package victor.training.spring.web.service;

public class DuplicatedTrainingException extends RuntimeException {
   public DuplicatedTrainingException() {
   }

   public DuplicatedTrainingException(String message) {
      super(message);
   }

   public DuplicatedTrainingException(String message, Throwable cause) {
      super(message, cause);
   }

   public DuplicatedTrainingException(Throwable cause) {
      super(cause);
   }

   public DuplicatedTrainingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
