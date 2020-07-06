package victor.training.spring.ms.bootservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
