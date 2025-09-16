package victor.training.spring.web.controller.util;

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
import victor.training.spring.web.MyException;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice // vin aici toate ex scapate din orice metoda @GetMapping&friends
public class GlobalExceptionHandler {
  private final MessageSource messageSource;

  // TODO handle any Exception subtype so exception stack traces are not exposed to clients; return code 500.
  //  To test, try to violate a validation rule enforced previously
  @ExceptionHandler(Exception.class) // tata la tot raul.
//  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ResponseEntity<String> onException(Exception exception) {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(500)
        .header("x-kone","crane")
        .body(exception.getMessage());
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

  // TODO handle NoSuchElementException to return 400 with body = "Not Found"

  // Return internationalized error messages in the user language from:
  // - the 'Accept-Language' request header via request.getLocale())
  // - the language in the Access Token via SecurityContextHolder

  // Example: mapping application exceptions to [internationalize-able] error messages in messages.properties
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(MyException.class)
  public String onMyException(MyException exception, HttpServletRequest request) throws Exception {
    String errorMessageKey = "error." + exception.getCode().name();
    Locale clientLocale = request.getLocale(); // or from the Access Token
    String responseBody = messageSource.getMessage(
        errorMessageKey, exception.getParams(), exception.getCode().name(), clientLocale);
    log.error(exception.getMessage() + " : " + responseBody, exception);
    return responseBody;
  }


}
