package victor.training.spring.web.controller.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import victor.training.spring.web.MyException;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice // #must have, sa nu scapi stack trace de exceptii clientilor = security brici🙁🙁🙁🙁
@RequiredArgsConstructor
public class GlobalExceptionHandler {
  private final MessageSource messageSource;

  @ResponseStatus(INTERNAL_SERVER_ERROR) //500
  @ExceptionHandler(Exception.class)// pt orice exceptie scapa (fallback)
  public String onException(Exception exception, HttpServletRequest request) throws Exception {
    if (exception instanceof AccessDeniedException) {
      throw exception; // allow 403 to go out
    }
    log.error(exception.getMessage(), exception);
    return exception.getMessage(); // don't leak stack traces to clients (Security Best Practice)
  }

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(NoSuchElementException.class)
  // attempted first, as the exception is more specific than 'Exception' above
  public String noSuchElementException() {
    // oricand un optional.orElseThrow() -> ex aruncata e prinsa aici,
    // pe orice flow HTTP
    return "Not Found❌";
  }


  // Return internationalized error messages in the user language from:
  // - the 'Accept-Language' request header via request.getLocale())
  // - the language in the Access Token via SecurityContextHolder

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(MyException.class)
  public String onMyException(MyException exception, HttpServletRequest request) throws Exception {
    UUID errorId= UUID.randomUUID(); // vis de L2 support: sa fie trace parent (traceID)
    // propagat in toate sistemele in care requestul a ajuns peste REST sau mesaje!
    String errorMessageKey = "error." + exception.getCode().name();
    Locale clientLocale = request.getLocale(); // or from the Access Token
    String responseBody = messageSource.getMessage(errorMessageKey, exception.getParams(), exception.getCode().name(), clientLocale);
    log.error(exception.getMessage() + " id: " + errorId +" : " + responseBody, exception);
    return responseBody+" " + errorId;
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public String onJavaxValidationException(MethodArgumentNotValidException e) {
    String response = e.getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(", \n"));
    log.error("Validation failed. Returning: " + response, e);
    return response;
  }
}
