package victor.training.spring.web.controller.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public String defaultErrorHandler(Exception exception, HttpServletRequest request) throws Exception {
    log.error(exception.getMessage(), exception);
    // you may want to translate a message code in the request.getLocale() / OpenID user token
    return exception.getMessage(); // Security Best Practice: hide stack traces from API responses.
  }

  //	@ResponseStatus(NOT_FOUND)
  //	@ExceptionHandler(NoSuchElementException.class) // attempted first <-- it's more specific
  //	public String noSuchElementException() {
  //		return "Not Found";
  //	}

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public List<String> onJavaxValidationException(MethodArgumentNotValidException e) {
    List<String> response = e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
    log.error("Validation failed. Returning: " + response, e);
    return response;
  }
}
