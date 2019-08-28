package hello;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	private final MessageSource messageSource;
	public GlobalExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(MyException.class)
	public ResponseEntity<String> handleBusinessException(HttpServletRequest request,
														  MyException e) {
		log.error("MyException occured: " + e.getCode(), e);
		String message = messageSource.getMessage(e.getCode().code,
				e.getArgs(), request.getLocale());

        return ResponseEntity
        		.status(HttpStatus.INTERNAL_SERVER_ERROR)
        		.body(message);
	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
		log.error("General exception occured: " + e, e);
		String message = messageSource.getMessage(
				MyException.ErrorCode.GENERAL.code,
				null, request.getLocale());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(message);
	}
	

}