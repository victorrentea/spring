package victor.training.spring.web.controller.util;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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

}
