package victor.training.spring.ms.bootclient;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
public class ReservationRest {
   @GetMapping("reservation-names")
   public List<String> getNames() {
      RestTemplate rest = new RestTemplate();
      // cum pot sa demarshallizez o List<ReservationDto> din JSON-ul de raspuns?
//      List<ReservationDto>.class


//      rest.getForObject("http://localhost:8080/resevations", ReservationDto.class);
      return Collections.emptyList();
   }
}

@Data
class ReservationDto {
   private String name;
}
