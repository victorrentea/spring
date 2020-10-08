package victor.training.spring.ms.bootclient;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationController {
   private final RestTemplate rest;

   @GetMapping("reservation-names")
   public String getReservationNames() {


      ReservationsListDto list = rest.getForObject("http://boot-service/reservations", ReservationsListDto.class);

      return list.getReservations().stream().map(ReservationDto::getName).collect(Collectors.joining(","));


   }



   // TODO Hint: @Autowired Source   .output().send(MessBuilder.withPayload(str).build())

   // TODO remove below
//   @Autowired
//   private Source channel;


   // "CEA MAI GRAVA BOALA DIN PROIECTE CU MICORSERVICII ESTE FOLOSIREA EXCLUSIVA A REST PENTRU COMUNICATIA INTRE ELE"



   @PostMapping("reservation")
   public String createReservation(@RequestParam String name) {
      log.info("Rulez");
      ReservationDto dto = new ReservationDto();
      dto.setName(name);
      rest.postForObject("http://boot-service/reservations", dto, Void.class);

//      Message<String> message = MessageBuilder.withPayload(name).build();
//      channel.output().send(message);
      System.out.println("Message Sent");
      log.info("End");
      return "Created reservation id: ";
   }
}

@Data
class ReservationsListDto {
   private List<ReservationDto> reservations;
}

@Data
class ReservationDto {
   private String name;
}
