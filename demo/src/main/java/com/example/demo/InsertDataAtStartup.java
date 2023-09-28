package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static java.time.LocalDate.now;

@Profile("local")
@Component
public class InsertDataAtStartup {
  @Autowired
  private ReservationRepo reservationRepo;

  @EventListener(ApplicationStartedEvent.class)
  public void insertInitData() {
    Stream.of("Isac", "George", "Radu", "Tedy", "Andreea")
        .map(name -> new Reservation()
            .setName(name)
            .setCreationDate(now()))
        .forEach(reservationRepo::save);
  }
}
