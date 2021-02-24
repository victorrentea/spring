package victor.training.spring.web.controller.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.MyException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private final MessageSource messageSource;

	public GlobalExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus
	public String defaultErrorHandler(HttpServletRequest request, Exception exception) throws Exception {
		log.error(exception.getMessage(), exception);
		System.out.println("Browser locale :" + request.getLocale());
		// you may want to translate a message code in the request.getLocale()
		return exception.getMessage();
	}

	@ExceptionHandler(MyException.class)
	@ResponseStatus
	public String handleMyException(HttpServletRequest request, MyException exception) throws Exception {
		log.error(exception.getMessage(), exception);
		System.out.println("Browser locale :" + request.getLocale());
		// you may want to translate a message code in the request.getLocale()
		String userMessage = messageSource.getMessage(exception.getErrorCode().name(), null, request.getLocale());
		return userMessage;
	}

}
