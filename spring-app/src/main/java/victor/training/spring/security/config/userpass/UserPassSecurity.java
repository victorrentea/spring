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
import victor.training.spring.web.entity.UserRole;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Profile("userpass")
@Configuration
@EnableWebSecurity // (debug = true) // see the filter chain in use
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class UserPassSecurity {
  @PostConstruct
  public void hi() {
    log.warn("Using");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()); // OK since I never take <form> POSTs

    // http.cors(Customizer.withDefaults()); // only if .js files come from a CDN (by default CORS requests get blocked)

    http.authorizeHttpRequests(authz -> authz
            .anyRequest().authenticated()
    );

    http.formLogin(Customizer.withDefaults()) // display a login page
            .userDetailsService(userDetailsService()); // distinguish vs Actuator user/pass

    http.httpBasic(Customizer.withDefaults()) // also accept Authorization: Basic ... request header
            .userDetailsService(userDetailsService()); // distinguish vs Actuator user/pass

    return http.build();
  }

  // *** Dummy users with plain text passwords - NEVER USE IN PRODUCTION
  @Bean
  public UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager(
            createUser("user", UserRole.USER),
            createUser("admin", UserRole.ADMIN),
            createUser("power", UserRole.POWER));
  }

  private UserDetails createUser(String username, UserRole role) {
    List<String> authorities = new ArrayList<>();
    authorities.add("ROLE_" + role.name());
    for (String permission : role.getSubRoles()) {
      authorities.add("ROLE_" + permission);
    }
    return User.withDefaultPasswordEncoder()
            .username(username)
            .password(username)
            .authorities(authorities.toArray(new String[0]))
            .build();
  }

}
