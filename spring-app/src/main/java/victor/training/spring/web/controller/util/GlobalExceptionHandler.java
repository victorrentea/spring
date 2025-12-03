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
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice // #mushave
@RequiredArgsConstructor
public class GlobalExceptionHandler {
  private final MessageSource messageSource;

  @ExceptionHandler(Exception.class) // catch-all fallback
  @ResponseStatus(INTERNAL_SERVER_ERROR) // 500
  public String onException(Exception exception, HttpServletRequest request) throws Exception {
    String errorId = UUID.randomUUID().toString();// next level: sa folosesti traceparent/traceId
    log.error(exception.getMessage() + " errorId: "+ errorId, exception);
    return exception.getMessage() + " errorId: errorId"; // don't leak stack traces to clients (Security Best Practice)
  }

  @ExceptionHandler(NoSuchElementException.class) // se aplica pt toate endpointurile din app
  public ResponseEntity<String> onNoSuchElementException(NoSuchElementException exception) {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(404).body("Not Found");
  }


  //	@ResponseStatus(NOT_FOUND)
  //	@ExceptionHandler(NoSuchElementException.class) // attempted first, as the exception is more specific than 'Exception' above
  //	public String noSuchElementException() {
  //		return "Not Found";
  //	}

  // Return internationalized error messages in the user language from:
  // - the 'Accept-Language' request header via request.getLocale())
  // - the language in the Access Token via SecurityContextHolder

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(MyException.class)
  public ResponseEntity<String> onMyException(MyException exception, HttpServletRequest httpRequest) throws Exception {
    String errorMessageKey = "error." + exception.getCode().name() + ".message";
    Locale clientLocale = httpRequest.getLocale(); // "Accept-Language: " or from the Access Token
    String responseBody = messageSource.getMessage(errorMessageKey,
        exception.getParams(), exception.getCode().name(), clientLocale);
    log.error(exception.getMessage() + " : " + responseBody, exception);
    String codeMessageKey =  "error." + exception.getCode().name() + ".code";
    int status = Integer.valueOf(messageSource.getMessage(codeMessageKey, new Object[0], "500", clientLocale));
    return ResponseEntity.status(status).body(responseBody);
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
