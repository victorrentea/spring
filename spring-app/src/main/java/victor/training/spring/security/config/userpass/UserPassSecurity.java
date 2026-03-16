package victor.training.spring.security.config.userpass;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Map;

@Slf4j // lombok
@Profile("userpass")// not needed in your case
@Configuration // allows @Bean
@EnableWebSecurity // (debug = true) // see the filter chain in use
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true) // enables the use of @Secured
public class UserPassSecurity {
  @PostConstruct
  public void hi() {
    log.warn("Using");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()); // OK since I never take <form> POSTs

    // you need this to allow JS REST CALLS from .js loaded from node js eg localhost:4200
     http.cors(Customizer.withDefaults()); // only if .js files come from a CDN (by default CORS requests get blocked)
    // also see GlobalCorsConfig

    http.authorizeHttpRequests(authz -> authz
            .anyRequest().authenticated()
    );

    http.formLogin(Customizer.withDefaults()) // display a login page user+pass
            .userDetailsService(userDetailsService()); // distinguish vs Actuator user/pass

    // this alllow you to call your apis using BasicAuth: Authorization: Basic base64(<user>:<pass>)
    // curl http://localhost:8080/api/trainings -u user:user
//    https://en.wikipedia.org/wiki/Basic_access_authentication
    http.httpBasic(Customizer.withDefaults()) // also accept Authorization: Basic ... request header
            .userDetailsService(userDetailsService()); // distinguish vs Actuator user/pass

    return http.build();
  }

  // *** Dummy users with plain text passwords - NEVER USE IN PRODUCTION
  @Bean
  public UserDetailsService userDetailsService(/*@Value("${user.to.pass}") Map<String, String > map*/) {
    UserDetails user = User.builder()
//            .username("user").password("user").roles("USER").build();
            .username("user")
        // for the demo https://bcrypt-generator.com/ generates for "user" the following string
        // NEXT STEP: load a set of user/{bcrypt}pass from properties / DB
            .password("{bcrypt}$2a$12$xidzKA7Elsd3Vo6tFtr5Oeeq7j.WbQulG2SHpVldFeRgkY4ty6f8u")
            .roles("USER")
        .build();
    UserDetails admin = User.withDefaultPasswordEncoder()
            .username("admin").password("admin").roles("ADMIN").build();
    UserDetails power = User.withDefaultPasswordEncoder()
            .username("power").password("power").roles("POWER").build();

    //As an impressive add-on, it would be to load the coarse-grained role from the database,
    // and after loading it, expand it to find fine-grained privileges in the same spirit that
    // Picnic Co does. JdbcUserDetailsManagers
    return new InMemoryUserDetailsManager(user, admin, power);
  }

}
