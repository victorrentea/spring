package victor.training.spring.web.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.web.security.KeycloakUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
public class CallingMicroController {
   @GetMapping("/api/micro")
   public String callMicro() throws URISyntaxException {
      RestTemplate rest = new RestTemplate();

//      String accessToken = KeycloakUtil.securityContext().getTokenString();
      String accessToken = "joke";
      Map<String, List<String>> header = Map.of("Authorization", List.of("Bearer " + accessToken));
      ResponseEntity<String> response = rest.exchange(new RequestEntity<>(
          CollectionUtils.toMultiValueMap(header),
          HttpMethod.GET,
          new URI("http://localhost:8082/")), String.class);

      return response.getBody();
   }
}
