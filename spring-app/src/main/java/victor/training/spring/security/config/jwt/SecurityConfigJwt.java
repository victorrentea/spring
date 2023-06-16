package victor.training.spring.security.config.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Profile("jwt")
@EnableWebSecurity
@EnableGlobalMethodSecurity
     (securedEnabled = true, //@Secured
     prePostEnabled = true) //@PreAuthorize("hasRole('ADMIN')")
public class SecurityConfigJwt extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // antipattern: pt ca daca modifici in @XyzMapping urlul vei uita sa modifici si aici.
//                .mvcMatchers(HttpMethod.DELETE,"/api/trainings/*").hasAuthority("training.delete")

                .mvcMatchers("/unsecured/**").permitAll() // pagina de panica

                .anyRequest().authenticated();
        http.csrf().disable(); // OK daca expui strict doar REST API (adica nu .jsp .jsf)
        http.authenticationProvider(preAuthenticatedProvider())
            .addFilter(jwtFilter());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public AuthenticationProvider preAuthenticatedProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(preauthUserDetailsService());// to load more user info
        return provider;
    }
    @Bean
    protected AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> preauthUserDetailsService() {
        return token -> (JwtPrincipal) token.getPrincipal();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtFilter jwtFilter() throws Exception {
        return new JwtFilter(authenticationManagerBean());
    }


}
