//package victor.training.spring.web;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.*;
//import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Slf4j
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfigDummy extends WebSecurityConfigurerAdapter {
//
//   @Override
//   protected void configure(HttpSecurity http) throws Exception {
//      http
//          .csrf().disable()
//          .authorizeRequests()
//               .anyRequest().authenticated()
//          .and()
//          .addFilterBefore(requestParamAuthorizationFilter(), BasicAuthenticationFilter.class)
//         ;
//   }
//
//   @Bean
//   public RequestParamFakeAuthorizationFilter requestParamAuthorizationFilter() throws Exception {
//      return new RequestParamFakeAuthorizationFilter(authenticationManagerBean());
//   }
//   @Slf4j
//   public static class RequestParamFakeAuthorizationFilter extends AbstractPreAuthenticatedProcessingFilter {
//      public RequestParamFakeAuthorizationFilter(AuthenticationManager authenticationManager) {
//         setAuthenticationManager(authenticationManager);
//      }
//      @Override
//      protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
//         String username = request.getParameter("user"); // /api/training/1?user=admin
//         if (username == null || username.isBlank()) {
//            log.error("No request param 'user' found on request");
//            return null;
//         }
//         log.info("Pre-authenticating with user={} ", username);
//         return username;
//      }
//      @Override
//      protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
//         return "N/A";
//      }
//   }
//
//   @Bean
//   public AuthenticationProvider preAuthenticatedProvider() {
//      PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
//      provider.setPreAuthenticatedUserDetailsService(new LoadUserForPreAuthenticatedUsername());
//      return provider;
//   }
//   public static class LoadUserForPreAuthenticatedUsername implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
//      public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
//         String username = (String) token.getPrincipal();
//         log.debug("Successful fake login");
//         return User.withUsername(username).roles(username.toUpperCase()).password("no").build();
//      }
//   }
//}
