package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler
  public String onAnyException(Exception e) {
    log.error("Error", e);
    return "Oups, mai baga o fisa🤪!";
  }
}
