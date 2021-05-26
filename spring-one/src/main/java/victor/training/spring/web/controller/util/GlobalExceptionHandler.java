package victor.training.spring.web.controller.util;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import victor.training.spring.web.SpaException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class) // fallback
	public String defaultErrorHandler(HttpServletRequest request, Exception exception) throws Exception {
		log.error(exception.getMessage(), exception);
		// you may want to translate a message code in the request.getLocale()
		return exception.getMessage();
	}
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = SpaException.class)
	public String spaExceptionHandler(HttpServletRequest request, SpaException exception) throws Exception {
		log.error(exception.getMessage(), exception);

		String translationKey = "error." + exception.getCode();
		Locale userLocale = request.getLocale();
//		Locale userLocale = useruCurentLogat.getLocale();
//		TranslationBundle.translate(translationKey, userLocale);
		String translatedMessage = messageSource.getMessage(translationKey, null, userLocale);
		return translatedMessage;
	}

	private final MessageSource messageSource;
}
