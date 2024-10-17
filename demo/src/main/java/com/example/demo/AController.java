package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

//  @PostMapping
  @GetMapping("/create")
  public int create(
      @RequestParam(defaultValue = "John Doe") String name) throws Exception {
    //return service.create(name);
    //service.createAtomic(name);
    //return service.createAtomic(name);
    return service.createReadOnly(name);
    //return service.createWithException(name);
//    return service.createWithIOException(name);
  }
}
