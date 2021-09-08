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

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Profile("local")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
          .csrf().disable() // or  .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
          .authorizeRequests()
               .mvcMatchers("/admin/**").hasRole("ADMIN")
               .mvcMatchers("/unsecured/**").permitAll()
               .anyRequest().authenticated() // orice URL
          .and()
          .formLogin().permitAll().and()
//          .httpBasic()
          ;
   }

   // *** Dummy users 100% in-mem - NEVER USE IN PRODUCTION
   @Bean
   public UserDetailsService userDetailsService() {
      UserDetails userDetails = User.withDefaultPasswordEncoder()
          .username("user").password("user").roles("USER").build();
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
