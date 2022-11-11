package victor.training.spring.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Profile("local")
@EnableWebSecurity // (debug = true) // see the filter chain in use
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // iti permite sa folosesti @PreAuthorized si/sau @Secured in app
public class DummySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // OK sa disable protectia default de la Spring
        // since I never take <form> POSTs (faceam asta cand ;ucram cu JPS,JSF,VAADIN
        // daca tu ai REST API-uri (doar JSOANe raspunzi) -> nu poti fi atacat cu CSRF

        http.cors(); // needed only if .js files are served by a CDN (eg)

        http.authorizeRequests()
//                .mvcMatchers(HttpMethod.DELETE, "/api/trainings/*").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.formLogin().defaultSuccessUrl("/", true);

        http.httpBasic(); // also accept Authorization: Basic ...
    }

   // *** Dummy users 100% in-mem - NEVER USE IN PRODUCTION
   @Bean
   public UserDetailsService userDetailsService() {
      UserDetails userDetails = User.withDefaultPasswordEncoder()
          .username("user").password("user").roles("USER")/*.authorities("training.delete")*/.build();
      UserDetails power = User.withDefaultPasswordEncoder()
          .username("power").password("power").roles("POWER").build();
      UserDetails adminDetails = User.withDefaultPasswordEncoder()
          .username("admin").password("admin").roles("ADMIN").build();
      return new InMemoryUserDetailsManager(userDetails, adminDetails, power);
   }

}
