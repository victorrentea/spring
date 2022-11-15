package victor.training.spring.web.controller;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND) // http response status
public class StatusException extends RuntimeException{
}
