package victor.training.spring.web.controller.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
