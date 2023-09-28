package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data // nu in app reale, doar in joaca
public class Reservation {
  @Id
  @GeneratedValue
  private Long id;
  @NotNull
  @Size(min = 4, message = "prea scurt")
  private String name;
  private LocalDate creationDate;
}
