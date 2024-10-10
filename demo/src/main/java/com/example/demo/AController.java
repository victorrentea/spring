package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {
  private final AService service;

  public AController(AService service) {
    this.service = service;
  }

  @GetMapping
  public String hi() {
    return service.hi();
  }
}
