package victor.training.spring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import victor.training.spring.web.security.DatabaseUserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   // TODO [SEC] Start with ROLE-based authorization on URL-patterns

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
          .csrf().disable()
          .authorizeRequests()
             .mvcMatchers("unsecured/*").anonymous()
             .mvcMatchers("admin/*").hasRole("ADMIN")
//             .mvcMatchers(HttpMethod.DELETE, "rest/trainings/{id}").hasRole("ADMIN")
             .anyRequest().authenticated()
          .and()
//          .formLogin().permitAll()
      .httpBasic()
      // unsecured/welcome-info la toti
      // admin/restart la admin
      // DELETE pe rest/trainings/{id} doar admin
      // OK in rest pt celelalte sa fii autentificat cu orice user, sa fii trecut de login doar.
      ;
   }

   // *** Dummy users 100% in-mem
//   @Bean
//   public UserDetailsService userDetailsService() {
//      UserDetails userDetails = User.withDefaultPasswordEncoder().username("user").password("parola").roles("USER").build();
//      UserDetails adminDetails = User.withDefaultPasswordEncoder().username("admin").password("parola").roles("ADMIN").build();
//      UserDetails admin2Details = User.withDefaultPasswordEncoder().username("admin2").password("parola").roles("ADMIN").build();
//      return new InMemoryUserDetailsManager(userDetails, adminDetails, admin2Details);
//   }

//     ... then, switch to loading user data from DB:
//     *** Also loading data from DB
    @Bean
    public UserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

}
