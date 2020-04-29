package victor.training.spring.web;

import org.springframework.context.annotation.Bean;

//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  /*extends WebSecurityConfigurerAdapter*/ {

    // TODO [SEC] Start with ROLE-based authorization on URL-patterns

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//       http
//            .csrf().disable()
//            ;
//    }

    // *** Dummy users 100% in-mem
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User.withDefaultPasswordEncoder()...
//        return new InMemoryUserDetailsManager(userDetails, adminDetails);
//    }

    // ... then, switch to loading user data from DB:
    // *** Also loading data from DB
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new DatabaseUserDetailsService();
//    }

}
