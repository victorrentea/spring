package com.example.demo;

import com.example.demo.DemoApplication.ReservationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
  private final ReservationRepo reservationRepo;

  public void create(ReservationDto dto) {
    System.out.println(dto);
    if (dto.name().contains("s")) throw new IllegalArgumentException("No S allowed");
    Reservation reservation = new Reservation();
    reservation.setName(dto.name());
    reservationRepo.save(reservation);
  }

  public List<ReservationDto> findAll() {
    return reservationRepo.findAll().stream()
        .map(reservation -> new ReservationDto(reservation.getName()))
        .toList();
  }
}
