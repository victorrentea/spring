package victor.training.spring.web.controller.util;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.MyException;

import java.util.Locale;

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

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = MyException.class)
	public ResponseEntity<String> defaultErrorHandler(HttpServletRequest request, MyException exception) throws Exception {
		log.error(exception.getMessage(), exception);
		// you may want to translate a message code in the request.getLocale()
//		Locale locale = request.getLocale();

//		KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		principal.getKeycloakSecurityContext().getIdToken().getLocale()
		Locale locale = request.getLocale();
		String userMessage = messageSource.getMessage(exception.getErrorCode().name(), exception.getParams(), "Nu-i!", locale);
		String responseCodeStr = messageSource.getMessage(
				exception.getErrorCode().name()+".errorCode", null, "500", Locale.ENGLISH);
		return ResponseEntity.status(Integer.parseInt(responseCodeStr))
				.body(userMessage)
				;
	}

	private final MessageSource messageSource;

}
