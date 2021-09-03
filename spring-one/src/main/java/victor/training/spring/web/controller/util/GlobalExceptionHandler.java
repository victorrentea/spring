package victor.training.spring.web.controller.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import victor.training.spring.web.controller.GenericException;
import victor.training.spring.web.service.DuplicatedTrainingException;

import javax.servlet.http.HttpServletRequest;

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

	@ExceptionHandler(DuplicatedTrainingException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleDuplicatedTraining(DuplicatedTrainingException exception) {
		return exception.getMessage();
	}

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<String> handleGenericException(GenericException exception, HttpServletRequest request) {
		String userMessage = messageSource.getMessage(exception.getCode().name(), new String[]{"HARD_CODED_VALUE"}, request.getLocale());
		return ResponseEntity
			.status(exception.getCode().getHttpCode())
//			.hea
			.body(userMessage);
	}

}
