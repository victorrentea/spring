package victor.training.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import victor.training.spring.web.repo.UserRepo;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    // TODO [SEC] Start with ROLE-based authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/unsecured/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin();

    }
    //
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//    }
//
//    //Define a UserDetailsService
//        UserDetails userDetails = User.withDefaultPasswordEncoder()...
//        return new InMemoryUserDetailsManager(userDetails);
//}

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//                .username("test").password("test").roles("USER")
//                .authorities("seeList").build();
//        UserDetails adminDetails = User.withDefaultPasswordEncoder()
//                .username("admin").password("admin").roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(userDetails,adminDetails);
//    }
}
