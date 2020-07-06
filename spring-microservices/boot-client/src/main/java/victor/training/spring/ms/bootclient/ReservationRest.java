package victor.training.spring.ms.bootclient;

import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class ReservationRest {
   @GetMapping("reservation-names")
   public List<String> getNames() {
      RestTemplate rest = new RestTemplate();
      // cum pot sa demarshallizez o List<ReservationDto> din JSON-ul de raspuns?

      ResponseEntity<List<ReservationDto>> response = rest.exchange("http://localhost:8080/reservations",
          HttpMethod.GET, null,
          new ParameterizedTypeReference<List<ReservationDto>>() {
          });

      return response.getBody()
          .stream()
          .map(ReservationDto::getName)
          .collect(toList());
   }
}

@Data
class ReservationDto {
   private String name;
}
