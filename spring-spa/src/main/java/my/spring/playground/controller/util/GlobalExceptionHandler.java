package my.spring.playground.controller.util;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.spring.playground.SpaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	private final MessageSource messageSource;

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String defaultErrorHandler(HttpServletRequest request, Exception exception) throws Exception {
		log.error(exception.getMessage(), exception);

		String messageKey = "error.GENERAL";
		String message = messageSource.getMessage(messageKey, null, request.getLocale());
		// pseudo cod: luat prop din fisier

		return message;
	}
	@ExceptionHandler(value = SpaException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String spaErrorHandler(HttpServletRequest request, SpaException exception) throws Exception {
		log.error(exception.getMessage(), exception);
		String messageKey = "error." + exception.getErrorCode().name();
		return messageSource.getMessage(messageKey, null, request.getLocale());
	}

}
