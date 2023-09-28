package com.example.demo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

  public record CreateReservationRequest(@NotNull @Size(min=4) String name){}
  @PostMapping("reservations")
  public Long createReservation(@RequestBody @Valid CreateReservationRequest request) {
    Reservation reservation = new Reservation();
    reservation.setName(request.name());
    reservation.setCreationDate(now());
    return reservationRepo.save(reservation).getId();
  }

  @Value("${max.reservations}")
  private int size;
  @GetMapping("reservations")
  public List<ReservationBriefResponse> getAllReservations() {
    PageRequest pageRequest = PageRequest.of(0, size, Sort.Direction.DESC, "creationDate");

    return reservationRepo.findAll(pageRequest).stream()
        .map(reservation -> new ReservationBriefResponse(reservation.getId(), reservation.getName()))
        .toList();
  }

}

