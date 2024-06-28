package com.example.demo;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SpringBootApplication
@Slf4j
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  // http://localhost:8080/hello/12?style=slay
  @GetMapping("hello/{id}")
  public String hello(
      @PathVariable long id,
      @RequestParam(required = false) String style) {
    return "Hello " + id + " ? " + style;
  }

  @Autowired
  ReservationService reservationService;

  record ReservationDto(@Schema(description = "Name of person for which is the reservation")
                        @Size(min = 10) String name) {
  }

  @PostMapping
  public ResponseEntity<Void> createReservation(@Validated @RequestBody ReservationDto dto) {
//    try {
    reservationService.create(dto);
//    } catch (IllegalArgumentException e) {
//      return ResponseEntity.badRequest().build();
//    }
    return ResponseEntity.ok()
        .header("X-Custom", "BUN") // motiv bun pentru ResponseEntity
        .build();
  }

  @GetMapping
  @Operation(description = "Get all reservations")
  public List<ReservationDto> findAllReservations() {
    log.debug("Finding all reservations");
    meterRegistry.counter("finduri").increment(); // sau nr de loginuri, nr de tranzactii facute, bani castigati azi
    return reservationService.findAll();
  }

  @Autowired
  private MeterRegistry meterRegistry; // micrometer= framework spring pentru colectare metrici

  @Bean // enables @Timed
  public TimedAspect timedAspect() {
    return new TimedAspect(meterRegistry);
  }

  // apoi te duci la http://localhost:8080 dai 3 x refresh
  // apoi te duci la http://localhost:8080/actuator/prometheus si cauti textul "finduri"
}

// Convention over configuration = ideea ca daca nu specific nimic, se folosesc defaulturi bune
@Component
//@ConditionalOnClass(RedisKungFu.class) // daca e in classpath
//@ConditionalOnMissingBean(DataSource.class) // daca nu exista deja un astfel de bean
@ConditionalOnProperty( // un fel de @Profile pe steroizi
    value = "startup.debug",
    havingValue = "true",
    matchIfMissing = true)
class Init {
  @EventListener(ApplicationStartedEvent.class) // cand toate beanurile sunt gata
  public void init() {
    System.out.println("Debugging...");
  }
}

