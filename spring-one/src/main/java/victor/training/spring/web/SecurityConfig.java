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
import victor.training.spring.web.domain.UserRole;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
          .csrf().disable() // or  .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
          .authorizeRequests()

//               .mvcMatchers("/api/admin/**").hasRole("ADMIN") // @PreAuthorize on the class level
//               .mvcMatchers(HttpMethod.DELETE, "/api/trainings/*").hasRole("ADMIN") // bad practice, risky.
               .anyRequest().authenticated()
          .and()
          .formLogin().permitAll().and()
          .httpBasic();
   }

   // *** Dummy users 100% in-mem - NEVER USE IN PRODUCTION
   @Bean
   public UserDetailsService userDetailsService() {
      UserDetails userDetails = User.withDefaultPasswordEncoder()
          .username("user")
          .password("user")
          .authorities(UserRole.USER.getAuthorities().toArray(new String[0]))
          .roles("USER").build();
      UserDetails adminDetails = User.withDefaultPasswordEncoder()
          .username("admin")
          .password("admin")
          .authorities(UserRole.ADMIN.getAuthorities().toArray(new String[0]))
          .roles("ADMIN").build();
      return new InMemoryUserDetailsManager(userDetails, adminDetails);
   }

   // ... Load user data from DB:
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new DatabaseUserDetailsService();
//    }

}
