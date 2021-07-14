package victor.training.spring.web;

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

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@Profile("!test")
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    // TODO [SEC] Start with ROLE-based authorization on URL-patterns

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
           .csrf().disable()
           .authorizeRequests()
               .mvcMatchers("unsecured/**").anonymous()
//               .mvcMatchers(HttpMethod.DELETE, "api/trainings/*").hasRole("ADMIN") // gunoi. prost. niciodata. DE CE ? ca e dparte de cod
               .mvcMatchers("admin/**").hasRole("ADMIN")
               .anyRequest().authenticated()
           .and()
           .formLogin().permitAll()
            ;
    }

    // *** Dummy users 100% in-mem
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build();
        UserDetails adminDetails = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(userDetails, adminDetails);
    }

    // ... then, switch to loading user data from DB:
    // *** Also loading data from DB
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new DatabaseUserDetailsService();
//    }

}
