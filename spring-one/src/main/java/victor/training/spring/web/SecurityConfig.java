package victor.training.spring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // TODO [SEC] Start with ROLE-based authorization on URL-patterns

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
            .csrf().disable()
            .authorizeRequests()
               .anyRequest().authenticated()
            .and()
            .formLogin().permitAll()
       // unsecured/welcome-info la toti
       // admin/restart la admin
       // DELETE pe rest/trainings/{id} doar admin
       // in rest pt celelalte sa fii autentificat cu orice user, sa fii trecut de login doar.
            ;
    }
    // *** Dummy users 100% in-mem
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder().username("user").password("parola").roles("USER").build();
        UserDetails adminDetails = User.withDefaultPasswordEncoder().username("admin").password("parola").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(userDetails, adminDetails);
    }
/*

     ... then, switch to loading user data from DB:
     *** Also loading data from DB
    @Bean
    public UserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }*/

}
