package victor.training.spring.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import victor.training.spring.security.config.apikey.ApiKeyFilter;

@Order(10) // less than the default 100 => runs first picking up the actuator endpoints
@Configuration
@Getter
@Setter
@ConfigurationProperties("actuator.security")
public class ActuatorSecurity extends WebSecurityConfigurerAdapter {

  private String apiKey;
  // xor
  private String username;
  private String password;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // this security filter chain only applies to /actuator/** requests
    http.requestMatcher(EndpointRequest.toAnyEndpoint());

    http.authorizeRequests()
        // curl http://localhost:8080/actuator/health -v
        // /actuator/health is unsecured
        .requestMatchers(EndpointRequest.to("health")).permitAll()

        .anyRequest().permitAll(); // DON'T USE IN PROD! use👇
//          .anyRequest().hasAuthority("ACTUATOR"); // require authentication for all remaining /actuator/** requests


    // the principal is identified using:
    http.addFilter(new ApiKeyFilter(apiKey));
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
    return new InMemoryUserDetailsManager(actuatorUser);
  }
}
