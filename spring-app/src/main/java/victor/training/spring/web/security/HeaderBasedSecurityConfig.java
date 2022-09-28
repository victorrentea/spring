//package victor.training.spring.web.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
//import victor.training.spring.web.controller.util.HttpRequestFilterPrintingHeaders;
//
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class HeaderBasedSecurityConfig extends WebSecurityConfigurerAdapter {
//
//   @Override
//   protected void configure(HttpSecurity http) throws Exception {
//      http
//          .addFilterBefore(new PreAuthFilter(), WebAsyncManagerIntegrationFilter.class)
//          .csrf().disable() // as I don't ever take <form> POSTs
//          .authorizeRequests().anyRequest().authenticated()
//          .and()
//          .formLogin().permitAll()
//              .defaultSuccessUrl("/",true)
//              .and()
//          .httpBasic();
//   }
//
//}
