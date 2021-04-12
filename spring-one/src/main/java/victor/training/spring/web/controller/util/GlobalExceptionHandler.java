package victor.training.spring.web.controller.util;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.MyException;

// Advice Aspect Pointcut
@RequiredArgsConstructor
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

	private final MessageSource messageSource;


	@ResponseStatus
	@ExceptionHandler(value = MyException.class)
	public String myErrorHandler(HttpServletRequest request, MyException exception) throws Exception {
		log.error(exception.getMessage(), exception);
		// you may want to translate a message code in the request.getLocale()
		String userMessage = messageSource.getMessage(exception.getCode().name(), null, request.getLocale());
		return userMessage;
	}

}

