//package victor.training.spring.web.security.preauth;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//// this will allow going in just using headers, eg:
//// curl http://localhost:8080/api/trainings -H 'X-User: user' -H 'X-User-Roles: USER'
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class PreAuthHeaderSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // as I don't ever take <form> POSTs
//                .addFilterBefore(preAuthHeaderFilter(), BasicAuthenticationFilter.class)
//                .authenticationProvider(preAuthenticatedProvider())
//                .authorizeRequests().anyRequest().authenticated();
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        // trick to expose the standard AuthenticationManager bean
//        return super.authenticationManagerBean();
//    }
//    @Bean
//    public PreAuthHeaderFilter preAuthHeaderFilter() throws Exception {
//        return new PreAuthHeaderFilter(authenticationManagerBean());
//    }
//
//    @Bean
//    public AuthenticationProvider preAuthenticatedProvider() {
//        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
//        provider.setPreAuthenticatedUserDetailsService(token -> (PreAuthHeaderPrincipal) token.getPrincipal());
//        return provider;
//    }
//}
