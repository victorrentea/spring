package victor.training.spring.web.service;

import feign.RequestInterceptor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableFeignClients
@Configuration
public class FeignClientConfig {
  @Bean
  public RequestInterceptor propagateOAuthAccessTokenInFeignRequests() {
    return template -> {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (principal instanceof KeycloakPrincipal) {
        KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
        String token = keycloakPrincipal.getKeycloakSecurityContext().getTokenString();
        template.header("Authorization", "Bearer " + token);
      }
    };
  }
}
