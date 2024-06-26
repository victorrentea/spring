package com.example.demo;

import jakarta.validation.constraints.Size;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  // http://localhost:8080/hello/12?style=slay
  @GetMapping("hello/{id}")
  public String hello(
      @PathVariable long id,
		  @RequestParam(required = false) String style) {
    return "Hello " + id + " ? " + style;
  }
  record ReservationDto(@Size(min=10) String name){}
  @PostMapping
  public void createReservation(@Validated @RequestBody ReservationDto dto) {
    System.out.println(dto);
  }

}
//TODO ResponseEntity - de ce nu
