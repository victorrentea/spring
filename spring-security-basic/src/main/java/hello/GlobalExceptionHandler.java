package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
class GlobalExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MyException.class)
    @ResponseBody
//    @ResponseStatus
    public String handle(MyException e) {
        System.out.println("Caut mesajul erorii");
        Locale locale = new Locale("ES", "ES");
        String key = e.getCode().code;
        String message = messageSource.getMessage(key, null, locale);
        return message;
    }

}
