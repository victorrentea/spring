package victor.training.spring.security.config.preauth;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import victor.training.spring.security.config.AddFilterDSL;
import victor.training.spring.security.config.jwt.JwtPrincipal;
import victor.training.spring.security.config.keycloak.TokenRolesToLocalRoles;

import static victor.training.spring.security.config.keycloak.TokenRolesToLocalRoles.RoleLevel.CLIENT;

// this will allow going in just using headers, eg:
// curl http://localhost:8080/api/trainings -H 'X-User: user' -H 'X-User-Roles: USER'
@Slf4j
@Profile("preauth")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class PreAuthSecurity {
  @PostConstruct
  public void hi() {
    log.warn("Using");
  }
  @Bean
  @Order(2)
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf ->csrf.disable());
    http.authorizeHttpRequests(authz -> authz.anyRequest().authenticated());
    http.apply(AddFilterDSL.of(PreAuthFilter::new));
    http.authenticationProvider(preAuthenticatedProvider());
    http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // don't emit Set-Cookie
    return http.build();
  }

  @Bean
  public AuthenticationProvider preAuthenticatedProvider() {
    PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
    provider.setPreAuthenticatedUserDetailsService(token -> (PreAuthPrincipal) token.getPrincipal());
    return provider;
  }


  // i'm sorry: see https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter#accessing-the-local-authenticationmanager

}
