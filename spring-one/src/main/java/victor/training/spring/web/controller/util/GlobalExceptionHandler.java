package victor.training.spring.web.controller.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

	private final MessageSource messageSource;

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(HttpServletRequest request, Exception exception) throws Exception {


		log.error(exception.getMessage(), exception);
		// you may want to translate a message code in the request.getLocale()
		return exception.getMessage();
	}


	@ExceptionHandler(value = NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNoSuchElementException(NoSuchElementException exception, HttpServletRequest request) {
		log.error(exception.getMessage(), exception);
		Locale userLocale = request.getLocale();
		// or
//		token = SecurityContextHolder.getContext().getAuthentication().getPrincipal()
//		token.get.....getlocale()

		String userMessage = messageSource.getMessage("error.NOT_FOUND", null, userLocale);
		return userMessage;
	}
	

}
