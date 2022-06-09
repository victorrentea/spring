package victor.training.spring.web;

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

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DummySecurityConfig extends WebSecurityConfigurerAdapter {

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      CookieCsrfTokenRepository repo = new CookieCsrfTokenRepository();
      repo.setCookieHttpOnly(false);
      http
//          .addFilterBefore(new InspectingFilter(), WebAsyncManagerIntegrationFilter.class)
//          .cors().disable()
//          .csrf().csrfTokenRepository(repo).and() // as I don't ever take <form> POST
//
//

          .csrf().disable()
          // it's ok to disable if you expose ONLY REST APIs (if you never are submitted a <form>
              // if you only get AJAX calls, the risk translates to EVIL domain firing an AJAX request to VICTIM
              // but here, CORS protects you (EVIL is blocked by the browser to talk to victim (unless victim agrees))

          .authorizeRequests().anyRequest().authenticated()
          .and()
          .formLogin().permitAll().and()
          .httpBasic();
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

   // ... Load user data from DB:
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new DatabaseUserDetailsService();
//    }

}