package victor.training.spring.web.controller.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import victor.training.spring.web.DuplicateTrainingNameException;
import victor.training.spring.web.MyException;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice // kind of a global exception handler for all controllers
@RequiredArgsConstructor
public class GlobalExceptionHandler {
  private final MessageSource messageSource;

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class) // catch-all, if no other @ExceptionHandler method matches
  public String onException(Exception exception, HttpServletRequest request) throws Exception {
    if (exception instanceof AccessDeniedException) {
      throw exception; // allow 403 to go out
    }
    log.error(exception.getMessage(), exception);
    return exception.getMessage(); // don't leak stack traces to clients (Security Best Practice)
  }

  	@ResponseStatus(NOT_FOUND)
  	@ExceptionHandler(NoSuchElementException.class) // attempted first, as the exception is more specific than 'Exception' above
  	public String noSuchElementException() {
  		return "Not Found";
  	}
  	@ResponseStatus(BAD_REQUEST)
  	@ExceptionHandler(DuplicateTrainingNameException.class) // attempted first, as the exception is more specific than 'Exception' above
  	public String noDuplicateTrainingNameException() {
  		return "DuplicateTrainingNameException";
  	}

  // Return internationalized error messages in the user language from:
  // - the 'Accept-Language' request header via request.getLocale())
  // - the language in the Access Token via SecurityContextHolder

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(MyException.class)
  public ResponseEntity<String> onMyException(MyException exception, HttpServletRequest request) throws Exception {
    String errorMessageKey = "error." + exception.getCode().name();
    Locale clientLocale = request.getLocale(); // from HTTP request Accept-Language: or from the Access Token
    String responseBody = messageSource.getMessage(errorMessageKey, exception.getParams(), exception.getCode().name(), clientLocale);
    log.error(exception.getMessage() + " : " + responseBody, exception);
    String statusCodeKey = "error." + exception.getCode().name() + ".status";
    String statusCodeStr = messageSource.getMessage(statusCodeKey, null, "500", clientLocale);
    int statusCode = Integer.parseInt(statusCodeStr);
    return ResponseEntity.status(statusCode).body(responseBody);
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
