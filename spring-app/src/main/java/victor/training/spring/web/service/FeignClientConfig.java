package victor.training.spring.web.service;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignClientConfig {
//  @Bean
//  public RequestInterceptor propagateOAuthAccessTokenInFeignRequests() {
    // TODO FIX migration
//    return template -> {
//      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//      if (principal instanceof KeycloakPrincipal) {
//        KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
//        String token = keycloakPrincipal.getKeycloakSecurityContext().getTokenString();
//        template.header("Authorization", "Bearer " + token);
//      }
//    };
//  }
}
