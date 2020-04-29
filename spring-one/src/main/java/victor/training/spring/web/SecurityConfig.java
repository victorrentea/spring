package victor.training.spring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import victor.training.spring.web.security.DatabaseUserDetailsService;
import victor.training.spring.web.security.JwtAuthorizationHeaderFilter;

@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    // TODO [SEC] Start with ROLE-based authorization

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/unsecured/**").anonymous()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
        .httpBasic()
                .and()
        .authenticationProvider(preAuthenticatedProvider())
                .addFilterBefore(jwtFilter(), BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }
    @Bean
    public AuthenticationProvider preAuthenticatedProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(preauthUserDetailsService());
        return provider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthorizationHeaderFilter jwtFilter() throws Exception {
        return new JwtAuthorizationHeaderFilter(authenticationManagerBean());
    }

    @Bean
    protected DatabaseUserDetailsService preauthUserDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("test")
                .password("test")
                .roles("USER")
                .build();
        UserDetails adminDetails = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(userDetails, adminDetails);
    }

}
