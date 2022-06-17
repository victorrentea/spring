package victor.training.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ClientCredentials {
   @Autowired
   OAuth2RestTemplate restTemplateTunatPtOAuthClientCrendential;
   // https://medium.com/@bcarunmail/securing-rest-api-using-keycloak-and-spring-oauth2-6ddf3a1efcc2
   @GetMapping("/api/client-credentials")
   public String clientCredentials() {
      return restTemplateTunatPtOAuthClientCrendential.getForObject("http://localhost:8082", String.class);
   }


   @Configuration
   public static class ConfigureOAuth2RestTemplate {
      @Bean
      public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails details) {
         log.info("Trying to get the Access Token with Client Credential ...");
         OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(details);
         //Prepare by getting the AccessToken once
         oAuth2RestTemplate.getAccessToken();
         log.info("SUCCESS");
         return oAuth2RestTemplate;
      }
   }

}


