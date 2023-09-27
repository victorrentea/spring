package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationApi {
  @GetMapping("reservations/{id}")
  public String getReservationById() {
    return "Hello!";
  }
}
