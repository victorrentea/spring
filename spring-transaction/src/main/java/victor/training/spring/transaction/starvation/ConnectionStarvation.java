package victor.training.spring.transaction.starvation;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("profile/sheep")
@RequiredArgsConstructor
@SpringBootApplication
class SheepController {
  public static void main(String[] args) {
    SpringApplication.run(SheepController.class, args);
  }

  private final MeterRegistry meterRegistry;

  @Bean
  public TimedAspect timedAspect() {
    return new TimedAspect(meterRegistry);
  }


  private final SheepService service;

  @GetMapping("create")
  public Long createSheep(@RequestParam(required = false) String name) {
    if (name == null) {
      name = "Bisisica " + LocalDateTime.now();
    }
    log.debug("create " + name);
    return service.create(name);
  }

  @GetMapping("search")
  public List<Sheep> searchSheep(@RequestParam(defaultValue = "Bisisica%") String name) {
    log.debug("search for " + name);
    return service.search(name);
  }
}

@Slf4j
@Service
@RequiredArgsConstructor
class SheepService {
  private final SheepRepo repo;
  private final ShepardService shepard;

//  @Transactional // acu nu mai obtine si blocheaza proxy vreo conenx, ci ea este
    // luata doar de .save()
  public Long create(String name) {
      // antipattern de perf sa chemi un API rest
      // al altei echipe cu connection blocata pe tread (dintr-o metoda @Transactional)
    String sn = shepard.registerSheep(name); // Takes 1 second (HTTP call)
    Sheep sheep = repo.save(new Sheep(name, sn));
    return sheep.getId();
  }

  public List<Sheep> search(String name) {
    return repo.getByNameLike(name);
  }
}

@Slf4j
@Service
@RequiredArgsConstructor
class ShepardService {
  @SneakyThrows
  @Timed("cioban")
  public String registerSheep(String name) {
    //        return new RestTemplate()
    //                .getForObject("http://localhost:9999/api/register-sheep", SheepRegistrationResponse.class)
    //                .getSn();
    Thread.sleep(1000); // simulate slow network call
    return UUID.randomUUID().toString();
  }
}

@Data
class SheepRegistrationResponse {
  private String sn;
}

interface SheepRepo extends JpaRepository<Sheep, Long> {
    List<Sheep> getByNameLike(String name); // SELECT * FROM Sheep where name like ?
}
// daca vreti ceva similar si pentru HTTP calls, cauta "Feing Client baledung"

@Entity
@Data
class Sheep {
  @GeneratedValue
  @Id
  private Long id;

  private String name;
  private String sn;

  public Sheep() {
  }

  public Sheep(String name, String sn) {
    this.name = name;
    this.sn = sn;
  }
}
