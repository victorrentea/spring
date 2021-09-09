package victor.training.spring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import victor.training.spring.web.security.DatabaseUserDetailsService;

// disable temporarily - use SSO instead.
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
          // Filter . doFilter (HttpServletReqiest r) r.getHeader();  r.getContentType(); r.getSession
          .csrf().disable()
          .cors().and()
//          .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
          .authorizeRequests()
//               .mvcMatchers(HttpMethod.DELETE, "/api/training/*").hasRole("ADMIN")
               .mvcMatchers("/admin/**").hasRole("ADMIN")
               .mvcMatchers("/unsecured/**").permitAll()
               .anyRequest().authenticated() // orice URL
          .and()
          .formLogin().permitAll().and()
//          .addFilterBefore(new Filter() {
//             @Override
//             public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//             }
//          })
//          .httpBasic()
          ;
   }

   // *** Dummy users 100% in-mem - NEVER USE IN PRODUCTION
//   @Bean
//   public UserDetailsService userDetailsService() {
//      UserDetails userDetails = User.withDefaultPasswordEncoder()
//          .username("user").password("user").roles("USER").build();
//      UserDetails adminDetails = User.withDefaultPasswordEncoder()
//          .username("admin").password("admin").roles("ADMIN").build();
//      return new InMemoryUserDetailsManager(userDetails, adminDetails);
//   }

   // ... Load user data from DB:
    @Bean
    public UserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

}
