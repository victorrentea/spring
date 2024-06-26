package com.example.demo;

import com.example.demo.DemoApplication.ReservationDto;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

  @Autowired
  ReservationService reservationService;

  record ReservationDto(@Size(min = 10) String name) {
  }

  @PostMapping
  public ResponseEntity<Void> createReservation(@Validated @RequestBody ReservationDto dto) {
    try {
      reservationService.create(dto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok()
        .header("X-Custom", "BUN") // motiv bun pentru ResponseEntity
        .build();
  }
}

@Slf4j
@Service
@RequiredArgsConstructor
class ReservationService {
  public void create(ReservationDto dto) {
    System.out.println(dto);
    if (dto.name().contains("s")) throw new IllegalArgumentException("No S allowed");
  }
}
//TODO ResponseEntity - de ce nu
