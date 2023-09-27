package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static java.time.LocalDate.now;

@RestController
@RequiredArgsConstructor
public class ReservationApi {
  private final ReservationRepo reservationRepo;

  public record ReservationDetailsResponse(Long id, String name, LocalDate creationDate) {
  }
  @GetMapping("reservations/{id}")
  public ReservationDetailsResponse getReservationById(@PathVariable Long id) {
    Reservation reservation = reservationRepo.findById(id).orElseThrow();
    return new ReservationDetailsResponse(reservation.getId(), reservation.getName(), reservation.getCreationDate());
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
