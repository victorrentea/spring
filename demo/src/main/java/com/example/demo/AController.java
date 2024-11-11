package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AController {
  private final AService aService;
  
  @GetMapping // http://localhost:8080
  public String hello() {
    System.out.println("Chem pe "+ aService.getClass());
    return aService.metodaSmechera();
  }

}
