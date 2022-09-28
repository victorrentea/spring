package victor.training.spring.web.controller.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.netty.http.server.HttpServerRequest;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	@ResponseStatus(INTERNAL_SERVER_ERROR) // http response status
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(Exception exception) throws Exception {
		log.error(exception.getMessage(), exception);
		// you may want to translate a message code in the request.getLocale() / OpenID user token
		return exception.getMessage(); // Security Best Practice: hide stack traces from API responses.
	}

	@ResponseStatus(NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public void handleNoSuchElement(NoSuchElementException e) {
		log.debug("Buu: " + e);
	}
}
