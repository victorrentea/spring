package victor.training.spring.web.controller;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ClientCredentials { // CLient=Applicatie
   @Autowired
   OAuth2RestTemplate oauth2RestTemplate;
   // https://medium.com/@bcarunmail/securing-rest-api-using-keycloak-and-spring-oauth2-6ddf3a1efcc2
   @GetMapping("/api/client-credentials")
   public String clientCredentials() {
      return oauth2RestTemplate.getForObject("http://localhost:8082", String.class);
   }

   @Scheduled(fixedRate = 60*60*1000)
  public void vreauSaExecutUnApelRESTCatreAltMicroserviciuDarNuinNumeleUnuiUser_ciInNumeleAplicatiei() {
     // echivalentu lui apikey/basic

  }


   @Configuration
   public static class ConfigureOAuth2RestTemplate {
      @Bean
      public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails details) {
         log.info("Trying to get the Access Token with Client Credential ...");
         OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(details);
         //Prepare by getting the AccessToken once
          OAuth2AccessToken token = oAuth2RestTemplate.getAccessToken();
          System.out.println( token);
         log.info("SUCCESS");
         return oAuth2RestTemplate;
      }
   }

}


