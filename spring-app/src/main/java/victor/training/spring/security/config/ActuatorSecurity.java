package victor.training.spring.security.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import victor.training.spring.security.config.apikey.ApiKeyFilter;
import victor.training.spring.security.config.apikey.ApiKeySecurity;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ActuatorSecurity {
  @PostConstruct
  public void hi() {
    log.warn("Using");
  }

  @Value("${actuator.security.username}")
  private final String username;
  @Value("${actuator.security.password}")
  private final String password;

  @Order(1) // less than the MAX_INT default, thus has priority over the rest
  @Bean
  public SecurityFilterChain actuatorFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable());

    // this security filter chain only applies to /actuator/**
    http.securityMatcher(EndpointRequest.toAnyEndpoint());

    http.authorizeHttpRequests(authz -> authz
          // http://localhost:8080/actuator/health is unsecured
          .requestMatchers(EndpointRequest.to(HealthEndpoint.class)).permitAll()

          // the rest of actuator:
//          .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll() // ⚠️NOT IN PROD!
          .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
    );
 // AUthorization:
    http.httpBasic(Customizer.withDefaults()).userDetailsService(actuatorUserDetailsService());

    http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // don't emit Set-Cookie
    return http.build();
  }

  @Bean
  public UserDetailsService actuatorUserDetailsService() {
    UserDetails actuatorUser = User.builder()
        .username(username)
        .password(password)
        .roles("ACTUATOR").build();
    return new InMemoryUserDetailsManager(actuatorUser);
  }
}
