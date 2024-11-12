package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity // niciodata @Data
@Getter
@Setter
@Table(name="users")
public class User {
  @Id
  @GeneratedValue // dintr-o secventa xxxxxxxxx-rapid
  private Long id;
  @Email
  private String email;
  @NotNull
  private String name;
}

