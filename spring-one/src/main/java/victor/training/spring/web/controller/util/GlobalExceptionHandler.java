package victor.training.spring.web.controller.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.MyException;
import victor.training.spring.web.service.ThingNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

   private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

   private final MessageSource messageSource;

   public GlobalExceptionHandler(MessageSource messageSource) {
      this.messageSource = messageSource;
   }

   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ExceptionHandler(value = MyException.class)
   public String defaultErrorHandler(HttpServletRequest request, MyException exception) throws Exception {
      log.error(exception.getMessage(), exception);
      // you may want to translate a message code in the request.getLocale()
      return messageSource.getMessage(exception.getCode().name(), null, request.getLocale());
   }

   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ExceptionHandler(value = Exception.class)
   public String defaultErrorHandler(HttpServletRequest request, Exception exception) throws Exception {
      log.error(exception.getMessage(), exception);
      // you may want to translate a message code in the request.getLocale()
      return messageSource.getMessage("GENERAL", null, request.getLocale());
   }

   @ExceptionHandler(ThingNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public String method(ThingNotFoundException exception) {
      log.error("Not found", exception);
      return "NOT FOUND";
   }

}
