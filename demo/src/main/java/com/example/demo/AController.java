package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AController {
  private final AService aService;
  private final Props props;

  @GetMapping
  public String hi() {
    boolean ePananCraciun = LocalDate.now().isBefore(props.b());
    return aService.f() + ePananCraciun;
  }

}
// controller sa cheme service
// copy-paste la LoggerAspect.java
// adnotati cu @Log metoda din service chemata din controller
// observati in consola logul