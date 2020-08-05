package victor.training.spring.ms.bootservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("reservations")
public class ReservationRest {
   private final ReservationRepo repo;

   public ReservationRest(ReservationRepo repo) {
      this.repo = repo;
   }

   @GetMapping
   public List<ReservationDto> getAll() {
      return repo.findAll().stream().map(ReservationDto::new).collect(toList());
   }

   @GetMapping("{id}")
   public ReservationDto getById(@PathVariable Long id) {
      return repo.findById(id).map(ReservationDto::new).orElse(null);
   }

//   @PostMapping//(produces = "text/plain") -- asta cere ca requestul sa vin cu header Accept: */* fie ceva care sa includa text/plain
//   public void createReservation(@RequestBody ReservationDto dto) throws InterruptedException {
//      Thread.sleep(5*1000);
//      Reservation entity = new Reservation(dto.name);
//      repo.save(entity);
////      return entity.getId();
//   }
}


