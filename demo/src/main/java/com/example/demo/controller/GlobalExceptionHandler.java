package com.example.demo.controller;

import com.example.demo.DemoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleUnknownException(Exception exception) {
        exception.printStackTrace();
        return exception.getMessage();
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleDateParseException(DateTimeParseException exception) {
        exception.printStackTrace();
        return "Invalid date";
    }

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleDateParseException(DemoException exception, HttpServletRequest request) {
        exception.printStackTrace();
        return messageSource.getMessage("error." + exception.getErrorCode(),new Object[]{"test"}, request.getLocale());
    }
}
