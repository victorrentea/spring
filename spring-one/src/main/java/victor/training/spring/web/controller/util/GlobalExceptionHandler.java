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
import victor.training.spring.LocalizableException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.UUID;

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

	@ExceptionHandler(value = LocalizableException.class)
	public ResponseEntity<String> defaultErrorHandler(HttpServletRequest request, LocalizableException exception) throws Exception {
		UUID uuid = UUID.randomUUID();
		log.error(exception.getMessage() + uuid, exception);

		Locale locale = request.getLocale();
		String userMessage = messageSource.getMessage(
			"error." + exception.getErrorCode().name(),
			exception.getArgs(), locale);

		String statusCodeStr = messageSource.getMessage(
			"error." + exception.getErrorCode().name()+".code",
			null, Locale.ENGLISH);


//		return exception.getErrorCode().name();
		return ResponseEntity.status(Integer.parseInt(statusCodeStr))
//			.header()
			.body(userMessage);

	}

	@Autowired
	private MessageSource messageSource;

}

//class ErrorResponse {
//	private final ErrorCode code;
//	private final String userMessage;
//	private final UUID requestId;
//}
