package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {
   private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
   @ExceptionHandler
   @ResponseStatus
   public String handle(Exception e) {
      String id = UUID.randomUUID().toString();
      log.error("BUBA " + id, e);
      return "Shit happens. Check the logs. ref=" + id;
   }

   @Autowired
   private MessageSource messageSource;

   @ExceptionHandler
   @ResponseStatus
   public String handleMyEx(MyException e, HttpServletRequest request) {
      String id = UUID.randomUUID().toString();
      log.error("BUBA " + id, e);
      String message = messageSource.getMessage(e.getCode().name(), null, request.getLocale());
      return "Error code=" + e.getCode() + " ref=" + id + " message: " + message;
   }


}
