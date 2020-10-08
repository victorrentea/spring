package victor.training.spring.ms.bootservice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler
   public String handleException(Exception exception, HttpServletRequest request) {
//      request.getLocale();
      return exception.getMessage();
   }
}
