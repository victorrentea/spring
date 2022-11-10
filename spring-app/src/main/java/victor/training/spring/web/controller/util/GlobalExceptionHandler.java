package victor.training.spring.web.controller.util;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.MyException;
import victor.training.spring.web.MyException.ErrorCode;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	@ResponseStatus(INTERNAL_SERVER_ERROR) // http response status
	@ExceptionHandler(Exception.class) // orice subtip de exceptii = toate.
	public String defaultErrorHandler(Exception exception, HttpServletRequest request) throws Exception {
		log.error(exception.getMessage(), exception);
		// you may want to translate a message code in the request.getLocale() / OpenID user token
		return exception.getMessage(); // Security Best Practice: hide stack traces from API responses.
	}

	@ResponseStatus(NOT_FOUND) // http response status
	@ExceptionHandler(NoSuchElementException.class) // orice subtip de exceptii = toate.
	public String noSucheElement() throws Exception {
		return "nu-i";
	}


	@ResponseStatus(INTERNAL_SERVER_ERROR) // http response status
	@ExceptionHandler(MyException.class) // orice subtip de exceptii = toate.
	public String myException(MyException exception, HttpServletRequest request) throws Exception {
		String key = "error." + exception.getCode().name();
		log.error("TROSC", exception);
		// nu mereu conteaza limba browserului (ce vine pe req http)
		// ci deseori limba userului este persistat intr-un USERS table sau vine pe AccesToken (OAuth)
		// o sa scoti language useului din token!
		// inceoe cu SecurityContextHolder.getContext().getAuthentication().getPrincipal()....

		String userMessage = messageSource.getMessage(key, exception.getParams(), request.getLocale());
		return userMessage;
	}


	@Autowired
	private MessageSource messageSource;
}
