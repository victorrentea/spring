package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

import static java.time.LocalDate.now;

@RestController
@RequiredArgsConstructor
public class ReservationApi {
  private final ReservationRepo reservationRepo;

  @GetMapping("reservations/{id}")
  public Reservation getReservationById(@PathVariable Long id) {
    return reservationRepo.findById(id).orElseThrow();
  }

  @PostMapping("reservations")
  public Long createReservation(String name) {
    Reservation reservation = new Reservation();
    reservation.setName(name);
    reservation.setCreationDate(now());
    return reservationRepo.save(reservation).getId();
  }

  @GetMapping("reservations")
  public List<ReservationBriefResponse> getAllReservations() {
    return reservationRepo.findAll().stream()
        .map(reservation -> new ReservationBriefResponse(reservation.getId(), reservation.getName()))
        .toList();
  }

  @EventListener(ApplicationStartedEvent.class)
  public void insertInitData() {
    Stream.of("Isac", "George", "Radu", "Tedy")
        .map(name -> new Reservation()
            .setName(name)
            .setCreationDate(now()))
        .forEach(reservationRepo::save);
  }
}
