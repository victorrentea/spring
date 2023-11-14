package victor.training.spring.security.config.header;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.function.Consumer;

// this will allow going in just using headers, eg:
// curl http://localhost:8080/api/trainings -H 'X-User: user' -H 'X-User-Roles: USER'
@Profile("header")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class HeaderSecurity  {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf ->csrf.disable());

    http.authorizeHttpRequests(authz -> authz.anyRequest().authenticated());

    http.apply(new AddMyFilter());

    var provider = new PreAuthenticatedAuthenticationProvider();
    provider.setPreAuthenticatedUserDetailsService(token -> (HeaderPrincipal) token.getPrincipal());
    http.authenticationProvider(provider);

    http.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    return http.build();
  }

  // i'm sorry: see https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter#accessing-the-local-authenticationmanager
  private static class AddMyFilter extends AbstractHttpConfigurer<AddMyFilter, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) {
      AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
      http.addFilter(new HeaderFilter(authenticationManager));
    }

  }

}
