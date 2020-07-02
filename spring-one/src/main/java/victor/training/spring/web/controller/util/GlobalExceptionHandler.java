package victor.training.spring.web.controller.util;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.SpaException;
import victor.training.spring.web.SpaException.ErrorCode;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final MessageSource messageSource;
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(HttpServletRequest request, Exception exception) throws Exception {
		log.error(exception.getMessage(), exception);
		return exception.getMessage();
	}


	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = SpaException.class)
	public String spaErrorHandler(HttpServletRequest request, SpaException exception) throws Exception {
		ErrorCode errorCode = exception.getErrorCode();
		String messageKey = "error."+errorCode;

		String message = messageSource.getMessage(
			messageKey, exception.getArgs(), request.getLocale()); // in real-case, iei localeul din profilul userului curent logat.
		log.error(exception.getMessage(), exception);
		return message;
	}

}
