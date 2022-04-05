package victor.training.spring.varie;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.web.controller.dto.TrainingDto;

import java.net.URI;
import java.util.List;

public class RestClient {
   public static void main(String[] args) {
      RestTemplate rest = new RestTemplate();

      TrainingDto dto = rest.getForObject(
          "http://localhost:8081/api/trainings/{id}",
          TrainingDto.class,1);
      System.out.println(dto);

      dto.name+="X";
      rest.postForObject(
          "http://localhost:8081/api/trainings",
          dto, Void.class);

      List listOfMaps = rest.getForObject("http://localhost:8081/api/trainings",
          List.class);

      System.out.println(listOfMaps);
      System.out.println(listOfMaps.get(0).getClass()); // dar to voiai List<TrainingDto>


      RequestEntity<List<TrainingDto>> request = new RequestEntity<List<TrainingDto>>(HttpMethod.GET,
          URI.create("http://localhost:8081/api/trainings"));
      ResponseEntity<List<TrainingDto>> responseEntity = rest.exchange(request,
          new ParameterizedTypeReference<>() { });

      List<TrainingDto> dtoList = responseEntity.getBody();

      System.out.println(dtoList);
      System.out.println(dtoList.get(0).getClass()); // dar to voiai List<TrainingDto>

   }

}
