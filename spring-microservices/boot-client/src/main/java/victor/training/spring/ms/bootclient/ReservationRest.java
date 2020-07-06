package victor.training.spring.ms.bootclient;

import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class ReservationRest {
   private final RestTemplate rest;

   public ReservationRest(RestTemplate rest) {
      this.rest = rest;
   }

   @GetMapping("reservation-names")
   public String getNames() {

      // cum pot sa demarshallizez o List<ReservationDto> din JSON-ul de raspuns?

      ResponseEntity<List<ReservationDto>> response = rest.exchange(
          "http://boot-service/reservations",
          HttpMethod.GET, null,
          new ParameterizedTypeReference<List<ReservationDto>>() {
          });

      return response.getBody()
          .stream()
          .map(ReservationDto::getName)
          .collect(Collectors.joining(", "));
   }
}

@Data
class ReservationDto {
   private String name;
}
