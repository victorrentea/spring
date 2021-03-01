package victor.training.spring.web.controller.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.MyException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	public GlobalExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(HttpServletRequest request, Exception exception) throws Exception {
		log.error(exception.getMessage(), exception);
		// you may want to translate a message code in the request.getLocale()
		return exception.getMessage();
	}

	private final MessageSource messageSource;

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = MyException.class)
	public String myExceptionHandler(HttpServletRequest request, MyException exception) throws Exception {
		String userMessage = messageSource.getMessage(exception.getErrorCode().name(), exception.getArgs(), request.getLocale());
		log.error("Erorr Occured: " + userMessage, exception);
		// you may want to translate a message code in the request.getLocale()
		return userMessage;
	}

}
