package victor.training.spring.security.config.userpass;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Profile("userpass")
@EnableWebSecurity // (debug = true) // see the filter chain in use
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfigUserPass extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    http.csrf().disable(); // OK since I never take <form> POSTs

    CookieCsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();
    csrfTokenRepository.setCookieHttpOnly(false);
    http.csrf().csrfTokenRepository(csrfTokenRepository);
    // http.cors(); // needed only if .js files are served by a CDN (eg)

    http.authorizeRequests().anyRequest().authenticated();

    http.formLogin().defaultSuccessUrl("/", true);

    http.httpBasic(); // also accept Authorization: Basic ...
    http.userDetailsService(userDetailsService());
  }

  // *** Dummy users 100% in-mem - NEVER USE IN PRODUCTION
  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails userDetails = User.withDefaultPasswordEncoder()
            .username("user").password("user").roles("USER").authorities("training.delete").build();
    UserDetails adminDetails = User.withDefaultPasswordEncoder()
            .username("admin").password("admin").roles("ADMIN").build();
    return new InMemoryUserDetailsManager(userDetails, adminDetails);
  }

}
