package victor.training.spring.web.controller.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.MyException;

import java.util.NoSuchElementException;

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
	public String defaultErrorHandler(HttpServletRequest request, MyException exception) throws Exception {
		String userMessage = messageSource.getMessage(exception.getErrorCode().name(), null, request.getLocale());
		log.error(exception.getMessage(), exception);
		// you may want to translate a message code in the request.getLocale()
		return userMessage;
	}
	
	

//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(value = MethodArgumentNotValidException.class)
//	public String hide(HttpServletRequest request, Exception exception) throws Exception {
//		return "valeu pt hackeru cu curl.";
//	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = NoSuchElementException.class)
	public String notfound(HttpServletRequest request, NoSuchElementException exception) throws Exception {
		log.error(exception.getMessage(), exception);
		// you may want to translate a message code in the request.getLocale()
		return exception.getMessage();
	}

}
