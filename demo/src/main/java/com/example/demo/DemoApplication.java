package com.example.demo;

import com.example.demo.DemoApplication.ReservationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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

import java.util.List;

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

  record ReservationDto(@Schema(description = "Name of person for which is the reservation")
                        @Size(min = 10) String name) {
  }

  @PostMapping
  public ResponseEntity<Void> createReservation(@Validated @RequestBody ReservationDto dto) {
//    try {
      reservationService.create(dto);
//    } catch (IllegalArgumentException e) {
//      return ResponseEntity.badRequest().build();
//    }
    return ResponseEntity.ok()
        .header("X-Custom", "BUN") // motiv bun pentru ResponseEntity
        .build();
  }

  @GetMapping
  @Operation(description = "Get all reservations")
  public List<ReservationDto> findAllReservations() {
    return reservationService.findAll();
  }
}

//TODO ResponseEntity - de ce nu
