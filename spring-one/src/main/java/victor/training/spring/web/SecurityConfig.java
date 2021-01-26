package victor.training.spring.web;

import javafx.scene.chart.PieChart.Data;
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
import org.springframework.stereotype.Service;
import victor.training.spring.web.security.DatabaseUserDetailsService;
//import victor.training.spring.web.security.DatabaseUserDetailsService;

//import victor.training.spring.web.security.DatabaseUserDetailsService;
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("dummySecurity")
class DummySecurityConfig  extends WebSecurityConfigurerAdapter {

    // TODO [SEC] Start with ROLE-based authorization
}
//import victor.training.spring.web.security.DatabaseUserDetailsService;
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("!dummySecurity")
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    // TODO [SEC] Start with ROLE-based authorization

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
            .authorizeRequests()
               .mvcMatchers("/unsecured/**").anonymous()
               .mvcMatchers("/admin/**").hasRole("ADMIN")
               .anyRequest().authenticated()
            .and()
//            .sessionManagement().maximumSessions(1).and()
            .httpBasic().and()
            .formLogin().permitAll()
        ;
    }

    // *** Dummy users 100% in-mem
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//                .username("test")
//                .password("test")
//                .roles("USER")
//                .build();
//        UserDetails adminDetails = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(userDetails, adminDetails);
//    }

    @Bean
    public DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }
}
