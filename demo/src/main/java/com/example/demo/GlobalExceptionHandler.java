package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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


}
