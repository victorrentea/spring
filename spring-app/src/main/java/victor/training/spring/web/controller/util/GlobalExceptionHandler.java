package victor.training.spring.web.controller.util;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.MyException;
import victor.training.spring.web.service.DuplicatedTrainingNameException;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	@ResponseStatus(INTERNAL_SERVER_ERROR) // http response status
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(Exception exception, HttpServletRequest request) throws Exception {
		log.error(exception.getMessage(), exception);
		// you may want to translate a message code in the request.getLocale() / OpenID user token
		return exception.getMessage(); // Security Best Practice: hide stack traces from API responses.
	}

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(INTERNAL_SERVER_ERROR) // http response status
	@ExceptionHandler(MyException.class)
	public String myException(MyException exception, HttpServletRequest request) throws Exception {
		log.error(exception.getMessage(), exception);

		String userMessage = messageSource.getMessage("error." + exception.getCode().name(), null, request.getLocale());
		// you may want to translate a message code in the request.getLocale() / OpenID user token
		return userMessage; // Security Best Practice: hide stack traces from API responses.
	}

	@ResponseStatus(NO_CONTENT) // http response status
	@ExceptionHandler(value = NoSuchElementException.class)
	public String notFound(Exception exception) throws Exception {
		return exception.getMessage();
	}
	@ExceptionHandler(DuplicatedTrainingNameException.class)
	public String myspecial(Exception exception) throws Exception {
		return "Another training with that name already exists";
	}
//	public ResponseEntity<String> notFound(Exception exception, HttpServletRequest request) throws Exception {
//		return ResponseEntity.status(204).body(exception.getMessage());
//	}

}
