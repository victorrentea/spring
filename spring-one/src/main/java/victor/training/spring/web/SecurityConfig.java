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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import victor.training.spring.web.security.DatabaseUserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Profile("local")
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    // TODO [SEC] Start with ROLE-based authorization on URL-patterns

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
//           .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .ignoringAntMatchers("/external-api").and()
           .csrf().disable()


            .authorizeRequests()

//                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers("/unsecured/**").permitAll()
                .anyRequest().authenticated()

       .and()
       .formLogin().permitAll()
//          .formLogin().disable()
//           .httpBasic()

            ;
    }

    // *** Dummy users 100% in-mem
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build();
//        UserDetails adminDetails = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(userDetails, adminDetails);
//    }

    // ... then, switch to loading user data from DB:
    // *** Also loading data from DB
    @Bean
    public UserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

}
