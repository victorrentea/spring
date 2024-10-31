package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // ~ AOP
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public String handleException(Exception e) {
    log.error("Error:" + e.getMessage(), e);
    return "💩Error: " + e.getMessage();
  }
  @ExceptionHandler(IllegalArgumentException.class)
  public String handleIllegalArgumentException(IllegalArgumentException e) {
    return "💩Arg Error: " + e.getMessage();
  }
}
