package victor.training.spring.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // @Secured @PreAuthorized
public class DummySecurityConfig extends WebSecurityConfigurerAdapter {

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      CookieCsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();
      csrfTokenRepository.setCookieHttpOnly(false);
//          .addFilterBefore(new HttpRequestFilterPrintingHeaders(), WebAsyncManagerIntegrationFilter.class)
//          .cors().and()
//          .csrf().disable() // OK since I don't ever take <form> POSTs

      // Filtru de CORS nu merge decat daca zici asta.
//      http.cors(); // copii si eu dupa alta app care merge :)


//      http.csrf().csrfTokenRepository(csrfTokenRepository);

      http.csrf().disable(); // ca nu fac POST ci doar AJAX

      http.authorizeRequests().anyRequest().authenticated();

      http.formLogin().permitAll().defaultSuccessUrl("/",true);

      http.addFilter(new ApiKeyFilter());

      http.httpBasic();
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
