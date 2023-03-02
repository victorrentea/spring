package victor.training.spring.web.controller.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import victor.training.spring.web.MyException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
  private final MessageSource messageSource;

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public String onException(Exception exception, HttpServletRequest request) throws Exception {
    log.error(exception.getMessage(), exception);
    return exception.getMessage(); // don't leak stack traces to clients (Security Best Practice)
  }

  	@ResponseStatus(HttpStatus.NOT_FOUND)
  	@ExceptionHandler(NoSuchElementException.class) // attempted first, as the exception is more specific than 'Exception' above
  	public String noSuchElementException() {
  		return "Not Found";
  	}

  // you may want to translate a message code in the request.getLocale() / OpenID user token

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(MyException.class)
  public String onMyException(MyException exception, HttpServletRequest request) throws Exception {
    String errorMessageKey = "error." + exception.getCode().name();
    Locale clientLocale = request.getLocale(); // or from a JWT token
    String responseBody = messageSource.getMessage(errorMessageKey, exception.getParams(), exception.getCode().name(), clientLocale);
    log.error(exception.getMessage() + " : " + responseBody, exception);
    return responseBody;
  }

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public String onJavaxValidationException(MethodArgumentNotValidException e) {
    String response = e.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", \n"));
    log.error("Validation failed. Returning: " + response, e);
    return response;
  }
}
