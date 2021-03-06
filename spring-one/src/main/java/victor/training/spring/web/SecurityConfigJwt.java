package victor.training.spring.web;//package victor.training.spring.web;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.stereotype.Component;
//import victor.training.spring.web.repo.UserRepo;
//import victor.training.spring.web.security.DatabaseUserDetailsService;
//import victor.training.spring.web.security.JwtAuthorizationHeaderFilter;
//
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfigJwt  extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .mvcMatchers("/unsecured/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .authenticationProvider(preAuthenticatedProvider())
//                .addFilterBefore(jwtFilter(), BasicAuthenticationFilter.class)
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        ;
//
//    }
//
//    @Bean
//    public AuthenticationProvider preAuthenticatedProvider() {
//        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
//        provider.setPreAuthenticatedUserDetailsService(preauthUserDetailsService());
//        return provider;
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public JwtAuthorizationHeaderFilter jwtFilter() throws Exception {
//        return new JwtAuthorizationHeaderFilter(authenticationManagerBean());
//    }
//
//    @Bean
//    protected JwtDatabaseUserDetailsService preauthUserDetailsService() {
//        return new JwtDatabaseUserDetailsService();
//    }
//}
