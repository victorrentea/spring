package victor.training.spring.first;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice // un fel de AOP
public class GlobalExceptionHandler {
  private final MessageSource messageSource;

  @ExceptionHandler(Exception.class) // orice subclasa de
  // exc din asta sare din orice metoda REST
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handler(Exception e, HttpServletRequest r) {
    return messageSource.getMessage("eroare",
        new String[]{"Maldive"},
        r.getLocale());
  }
}
