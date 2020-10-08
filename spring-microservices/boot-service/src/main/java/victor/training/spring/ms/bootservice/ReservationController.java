package victor.training.spring.ms.bootservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping("reservations")
@RequiredArgsConstructor
public class ReservationController {
   private final ReservationRepo repo;

   @GetMapping
   public List<ReservationDto> getAll() {
      return repo.findAll().stream().map(ReservationDto::new).collect(toList());
   }

   @GetMapping("{id}")
   public ReservationDto getById(@PathVariable Long id) {
      return repo.findById(id).map(ReservationDto::new).orElse(null);
   }

   @PostMapping
   public void createReservation(@RequestBody ReservationDto dto) throws InterruptedException {
      Thread.sleep(5*1000);
      Reservation entity = new Reservation(dto.name);
      repo.save(entity);
   }

   // TODO Hint @ServiceActivator(inputCh="input")
//   @ServiceActivator(inputChannel = "input")
//   public void acceptNewReservation(String rn) {
//      log.info("Message received.");
//      repo.save(new Reservation(rn));
//   }
}


