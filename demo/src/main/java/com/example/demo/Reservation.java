package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


//@Data // genereaza:
// - toString pe toate campurile poate triggera lazy-loading de colectii
// - hashCode/equals include ID
@Entity
@Getter
@Setter
public class Reservation {
  @Id
  @GeneratedValue
  private Long id;
  private String name;
}
