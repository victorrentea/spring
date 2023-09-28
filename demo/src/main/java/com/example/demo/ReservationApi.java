package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.time.LocalDate.now;

@RestController
@RequiredArgsConstructor
public class ReservationApi {
  private final ReservationRepo reservationRepo;
  @Value("${max.reservations}")
  private int size;

  @GetMapping("reservations/{id}")
  public ReservationDetailsResponse getReservationById(@PathVariable Long id) {
    Reservation reservation = reservationRepo.findById(id).orElseThrow();
    System.out.println("Stuff");
    return new ReservationDetailsResponse(reservation.getId(), reservation.getName(), reservation.getCreationDate());
  }



  @Transactional
  @PostMapping("reservations")
  @Operation(description = "A fost odata ca-n povesti...")
  public Long createReservation(@RequestBody @Valid CreateReservationRequest request) {
    Reservation reservation = new Reservation();
    reservation.setName(request.name());
    reservation.setCreationDate(now());
    auditRepo.save(new Audit("S-a creat " + reservation));
    return reservationRepo.save(reservation).getId();
  }
  private final AuditRepo auditRepo;

  @GetMapping("reservations")
  public List<ReservationBriefResponse> getAllReservations() {
    PageRequest pageRequest = PageRequest.of(0, size, Sort.Direction.DESC, "creationDate");

    return reservationRepo.findAll(pageRequest).stream()
        .map(reservation -> new ReservationBriefResponse(reservation.getId(), reservation.getName()))
        .toList();
  }

  public record ReservationDetailsResponse(Long id, String name, LocalDate creationDate) {
  }

  public record CreateReservationRequest(
      @Schema(description = "Numele!") @NotNull @Size(min = 4) String name
  ) {
  }

}

