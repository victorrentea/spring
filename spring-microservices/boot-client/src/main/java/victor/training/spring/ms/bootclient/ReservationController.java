package victor.training.spring.ms.bootclient;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ReservationController {
   private final RestTemplate rest;

   public ReservationController(RestTemplate rest) {
      this.rest = rest;
   }

   @GetMapping("reservation-names")
   public String getReservationNames() {
      // cum pot sa demarshallizez o List<ReservationDto> din JSON-ul de raspuns?

      ResponseEntity<List<ReservationDto>> response = rest.exchange(
//          "http://localhost:8080/reservations",
          "http://boot-service/reservations", // TODO delete
          HttpMethod.GET, null,
          new ParameterizedTypeReference<List<ReservationDto>>() {
          });

      return response.getBody()
          .stream()
          .map(ReservationDto::getName)
          .collect(Collectors.joining(", "));
   }



   // TODO Hint: @Autowired Source   .output().send(MessBuilder.withPayload(str).build())

   // TODO remove below
   @Autowired
   private Source channel;

   @PostMapping("reservation")
   public String createReservation(@RequestParam String name) {
      log.info("Rulez");
//      ReservationDto dto = new ReservationDto();
//      dto.setName(name);
//      rest.postForObject("http://boot-service/reservations", dto, Void.class);

      Message<String> message = MessageBuilder.withPayload(name).build();
      channel.output().send(message);

      System.out.println("Message Sent");
      log.info("End");
      return "Created reservation id: ";
   }

}

@Data
class ReservationDto {
   private String name;
}
