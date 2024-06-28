package com.example.demo;

import com.example.demo.DemoApplication.ReservationDto;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

  @SneakyThrows
  @Timed("findAllReservations") // zicand ca ar putea dura mult
  // ca sa-ti mearga timed trebuie sa o activezi intai definind un bean TimedAspect


  // pe ce as pune din start by default @Timed: pe metode
  //      care suspectez ca ar putea dura prea mult / sa fie chemate prea des
  // - pe metode in care se face apicall catre exterior
  // - pe metode de repo
  // - pe metode care exporta/importa date
  // - pe metode care fac multe calcule (criptari, verificari de semnaturi)
  // !! sa nu pui @Timed peste tot pe met foarte simple, caci poti dauna performantei
  public List<ReservationDto> findAll() {
    log.debug("Cautand in disperare motivul crapaului din productie nereproductibil pe local");

    // scenariu: incepi un flux complex care implica cozi de mesaje
    // EU ---MESAJ--->B iar B --MESAJ--> EU
    long t0 = System.currentTimeMillis();
    Thread.sleep(100);
    //trimit t0 in mesajul catre B ca header iar B copiaza headerul mesajului in Rabbit pe care mi-l trimite inapoi
    long t1 = System.currentTimeMillis();// din headerul primit din B
    meterRegistry.timer("messageroundtrip").record(t1-t0, TimeUnit.MILLISECONDS);

    return reservationRepo.findAll().stream()
        .map(reservation -> new ReservationDto(reservation.getName()))
        .toList();
  }
  private final MeterRegistry meterRegistry;
}
