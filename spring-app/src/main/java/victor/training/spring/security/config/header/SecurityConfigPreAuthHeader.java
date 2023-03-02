package victor.training.spring.security.config.header;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

// this will allow going in just using headers, eg:
// curl http://localhost:8080/api/trainings -H 'X-User: user' -H 'X-User-Roles: USER'
@Profile("header")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
// allows me to use annotations to control access to methods
public class SecurityConfigPreAuthHeader extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable() // as I don't ever take  <form method="post" action="url..."><input type="submit"></form>
            .authorizeRequests().anyRequest().authenticated().and()
            .addFilter(preAuthHeaderFilter())
            .authenticationProvider(preAuthenticatedProvider())
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    ;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean(); // expose the standard AuthenticationManager bean
  }

  @Bean
  public PreAuthHeaderFilter preAuthHeaderFilter() throws Exception {
    return new PreAuthHeaderFilter(authenticationManagerBean());
  }// after we return the instance, spring looks at it and : processes additional
  // @Autowired, runs @PostConstruct hooks, may proxy if needed

  @Bean
  public AuthenticationProvider preAuthenticatedProvider() {
    PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
    provider.setPreAuthenticatedUserDetailsService(token -> (PreAuthHeaderPrincipal) token.getPrincipal());
    return provider;
  }
}
