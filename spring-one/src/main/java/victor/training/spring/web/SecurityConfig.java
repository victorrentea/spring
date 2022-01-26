//package victor.training.spring.web;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Profile("!test")
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//   @Override
//   protected void configure(HttpSecurity http) throws Exception {
//      http
//
//          .csrf().disable() // << safe if you only expose REST endpoints
//          // prevents creating a JSESSIOID cookie + HttpSession for each incoming request that lives 30 min
////          .sessionManagement()
//////            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////            .disable()  // only for browser-facing apps.
//
//          .authorizeRequests()
////            .mvcMatchers("admin/**").hasRole("ADMIN")
//            .anyRequest().authenticated()
//          .and()
//
//          .formLogin().permitAll()
//          .and()
//          .httpBasic();
//   }
//
//   // *** Dummy users 100% in-mem - NEVER USE IN PRODUCTION
//   @Bean
//   public UserDetailsService userDetailsService() {
//      UserDetails userDetails = User.withDefaultPasswordEncoder()
//          .username("user").password("user").roles("USER").build();
//      UserDetails adminDetails = User.withDefaultPasswordEncoder()
//          .username("admin").password("admin").roles("ADMIN").build();
//      return new InMemoryUserDetailsManager(userDetails, adminDetails);
//   }
//
//   // ... Load user data from DB:
////    @Bean
////    public UserDetailsService userDetailsService() {
////        return new DatabaseUserDetailsService();
////    }
//
//}
