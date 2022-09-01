package victor.training.spring.web.controller.util;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	@ResponseStatus(NO_CONTENT) // http response status
	@ExceptionHandler(value = NoSuchElementException.class)
	public String notFound(Exception exception) throws Exception {
		return exception.getMessage();
	}
//	public ResponseEntity<String> notFound(Exception exception, HttpServletRequest request) throws Exception {
//		return ResponseEntity.status(204).body(exception.getMessage());
//	}

}
