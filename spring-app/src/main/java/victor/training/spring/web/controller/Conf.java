package victor.training.spring.web.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

@Configuration
public class Conf {
   @Bean
   public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails details) {
      OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(details);

      //Prepare by getting access token once
      oAuth2RestTemplate.getAccessToken();
      return oAuth2RestTemplate;
   }
}
