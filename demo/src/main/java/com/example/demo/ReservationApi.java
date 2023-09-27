package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationApi {
  private final ReservationRepo reservationRepo;
  @GetMapping("reservations/{id}")
  public Reservation getReservationById(@PathVariable Long id) {
    return reservationRepo.findById(id).orElseThrow();
  }
}
