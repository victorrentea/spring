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

@Profile("userpass")
@EnableWebSecurity // (debug = true) // see the filter chain in use
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class UserPassSecurity extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable(); // OK since I only expose REST APIS and never take <form> POSTs eg jsp, vaadin, jsf

    // http.cors(); // needed only if .js files are served by a CDN (eg) and you want to enable CORS (by default CORS requests get blocked)

    http.authorizeRequests()
        .anyRequest().authenticated();

    http.formLogin().defaultSuccessUrl("/", true);

    http.httpBasic(); // also accept Authorization: Basic ...

    http.userDetailsService(userDetailsService());
  }

  // *** Dummy users 100% in-mem with plain text passwords - NEVER USE IN PRODUCTION
  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withDefaultPasswordEncoder()
            .username("user").password("user").roles("USER").build();
    UserDetails admin = User.withDefaultPasswordEncoder()
        .username("admin").password("admin").roles("ADMIN").build();
    UserDetails power = User.withDefaultPasswordEncoder()
        .username("power").password("power").roles("POWER").build();
    return new InMemoryUserDetailsManager(user, admin, power);
  }

}
