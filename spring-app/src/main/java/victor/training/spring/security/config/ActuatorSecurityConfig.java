package victor.training.spring.security.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import victor.training.spring.security.config.apikey.ApiKeyFilter;

@Order(10) // less than the default 100 => runs first picking up the actuator endpoints
@Configuration
@Getter @Setter
@Profile({"userpass","jwt","keycloak","apikey", "header"})
@ConfigurationProperties("actuator.security")
public class ActuatorSecurityConfig extends WebSecurityConfigurerAdapter {

  private String apiKey;
  // or
  private String username;
  private String password;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.requestMatcher(EndpointRequest.toAnyEndpoint())
        // this security filter chain applies to /actuator/** requests
        .authorizeRequests()
          // curl http://localhost:8080/actuator/health -v
          .requestMatchers(EndpointRequest.to("health")).permitAll() // unsecured endpoint
          .requestMatchers(EndpointRequest.to("prometheus"))
              .hasAnyAuthority("ACTUATOR", "PROMETHEUS") // unsecured endpoint
          .anyRequest().hasAuthority("ACTUATOR")

  //        .anyRequest().permitAll(); // DON'T USE IN PROD! instead:
    ; // require authentication for /actuator


    // the principal is identified using:
//    http.addFilter(new ApiKeyFilter(apiKey));
    // curl http://localhost:8080/actuator/prometheus -v -H 'x-api-key: secret'
    // -- xor --
    http.httpBasic().and().userDetailsService(actuatorUserDetailsService());
    // curl http://localhost:8080/actuator/prometheus -v -u actuator:actuator
  }

  @Bean
  public UserDetailsService actuatorUserDetailsService() {
    UserDetails actuatorUser = User.builder()
        .username(username)
        .password(password)
        .authorities("ACTUATOR").build();
    UserDetails prometheus = User.withDefaultPasswordEncoder()
        .username("p")
        .password("p")
        .authorities("PROMETHEUS").build();
    return new InMemoryUserDetailsManager(actuatorUser, prometheus);
  }
}
