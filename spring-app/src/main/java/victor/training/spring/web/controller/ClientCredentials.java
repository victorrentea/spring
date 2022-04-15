package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientCredentials {
   @Autowired
   OAuth2RestTemplate restTemplate;
   // https://medium.com/@bcarunmail/securing-rest-api-using-keycloak-and-spring-oauth2-6ddf3a1efcc2
   @GetMapping("/api/client-credentials")
   public String clientCredentials() {
      return restTemplate.getForObject("http://localhost:8082", String.class);
   }

}


