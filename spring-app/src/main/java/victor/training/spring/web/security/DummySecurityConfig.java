package victor.training.spring.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class DummySecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${api.key}")
    String apiKeyValue;
   @Override
   protected void configure(HttpSecurity http) throws Exception {
       ApiKeyFilter filter = new ApiKeyFilter();
       filter.setAuthenticationManager(new AuthenticationManager() {
           @Override
           public Authentication authenticate(Authentication authentication) throws AuthenticationException {
               if (authentication.getPrincipal() instanceof String) {
                   String apiKeyDePeHeader = (String) authentication.getPrincipal();
                   if (apiKeyDePeHeader.equals(apiKeyValue)) {
                       authentication.setAuthenticated(true);
                       return authentication;
                   }
               }
               throw new BadCredentialsException("Uknown api key");
           }
       });

       http
//          .addFilterBefore(new HttpRequestFilterPrintingHeaders(), WebAsyncManagerIntegrationFilter.class)
//          .cors().and()
          .csrf().disable() // ok daca expui doar REST API, si niciodata nu accepti <form> POSTs(adica daca nu generezi HTML de pe serverside)
          .authorizeRequests()

              // NICIODATA sa nu folositi URL-based authorization a la web.xml
//              .mvcMatchers(HttpMethod.DELETE, "/api/trainings/*").hasRole("ADMIN")

              .anyRequest().authenticated()
          .and()
          .formLogin().permitAll() // pt oameni
              // parole de oameni in baza mea ?
                  // " a stoca parole de OAMENI intr-o baza de date "
              //     NUUU: volosim KeyVault din Azure unde se gasesc parolele criptate (hashuite) de oameni
              .defaultSuccessUrl("/",true)
           .and()
          .httpBasic()
              .and().addFilter(filter); // app-to-app comm, "api-key"
   }

   // *** Dummy users 100% in-mem - NEVER USE IN PRODUCTION
   @Bean
   public UserDetailsService userDetailsService() {
      UserDetails userDetails = User.withDefaultPasswordEncoder()
          .username("user").password("user").roles("USER").authorities("training.delete").build();
      UserDetails adminDetails = User.withDefaultPasswordEncoder()
          .username("admin").password("admin").roles("ADMIN").build();
      return new InMemoryUserDetailsManager(userDetails, adminDetails);
   }

}
