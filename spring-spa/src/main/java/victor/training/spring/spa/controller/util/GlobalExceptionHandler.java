package victor.training.spring.spa.controller.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.spa.SpaException;

import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;


	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = SpaException.class)
	public String spaExceptionHandler(HttpServletRequest request, SpaException exception) throws Exception {
		Locale locale = request.getLocale();
		System.out.println("Locale: " + locale);
		String translatedMessage = messageSource.getMessage(exception.getErrorCode().messageKey, exception.getArgs(), locale);
		log.error(exception.getMessage(), exception);
		return translatedMessage;
	}
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(HttpServletRequest request, Exception exception) throws Exception {
		log.error(exception.getMessage(), exception);
		return exception.getMessage();
	}

}
