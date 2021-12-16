package victor.training.spring.web.controller.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import victor.training.spring.web.MyException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(HttpServletRequest request, Exception exception) throws Exception {
		log.error(exception.getMessage(), exception);
		// you may want to translate a message code in the request.getLocale()
		return exception.getMessage();
	}

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = MyException.class)
	public String handleMyException(HttpServletRequest request, MyException exception) throws Exception {
		log.error(exception.getMessage(), exception);
		String messageKey = "error." + exception.getErrorCode().name();
		Locale userLocale = request.getLocale(); // sau de pe userul curent logat care are locale in el
		String userMessage = messageSource.getMessage(messageKey, exception.getArgs(), userLocale);
		// you may want to translate a message code in the request.getLocale()
		return userMessage;
	}

}
