package victor.training.spring.ms.bootclient;

import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReservationRest {
   private final RestTemplate rest;

   public ReservationRest(RestTemplate rest) {
      this.rest = rest;
   }

   @GetMapping("reservation-names")
   public String getReservationNames() {
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

   @PostMapping("reservation")
   public String createReservation(@RequestParam String name) {
      ReservationDto dto = new ReservationDto();
      dto.setName(name);
      rest.postForObject("http://boot-service/reservations", dto, Void.class);
      return "Created reservation id: ";
   }

}

@Data
class ReservationDto {
   private String name;
}
