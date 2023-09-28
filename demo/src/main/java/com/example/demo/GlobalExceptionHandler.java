package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
  public String onAnyException(Exception e) {
    log.error("Error", e);
    return "Oups, mai baga o fisa🤪!";
  }
  @ExceptionHandler
  public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
    log.error("Error", e);
    return ResponseEntity.badRequest() //400
        .body("Bad Request: " + e.toString());
  }
}
