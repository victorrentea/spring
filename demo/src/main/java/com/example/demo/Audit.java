package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Audit {
  @Id
  @GeneratedValue
  private Long id;
  private String text;

  public Audit() {
  }

  public Audit(String text) {
    this.text = text;
  }
}
