package victor.training.spring.security.config.disabled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import jakarta.annotation.PostConstruct;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Profile("!userpass & !jwt & !keycloak & !apikey & !preauth")
@Configuration
@EnableWebSecurity
public class DisabledSecurity {

  @PostConstruct
  public void hi() {
    log.warn("Using");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable());
    http.authorizeRequests(authz -> authz.anyRequest().permitAll());
    return http.build();
  }
}
