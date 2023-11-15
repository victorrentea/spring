package victor.training.spring.security.config.apikey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Profile("apikey")
@Configuration
@EnableWebSecurity
public class ApiKeySecurity {

  @Value("${api-key:secret}")
  private String apiKey;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable());
    http.authorizeRequests(authz -> authz.anyRequest().authenticated());
    http.addFilter(new ApiKeyFilter(apiKey));
    http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // don't emit Set-Cookie
    return http.build();
  }

}
