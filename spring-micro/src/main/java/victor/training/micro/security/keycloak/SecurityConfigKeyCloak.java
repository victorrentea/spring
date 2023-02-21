package victor.training.micro.security.keycloak;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import victor.training.micro.security.keycloak.KeycloakResourceAuthenticationProvider;

@Profile("keycloak")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
class SecurityConfigKeyCloak extends KeycloakWebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, @Value("${keycloak.resource}") String clientName) {
        // extract roles from realm_access
        // var keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        // extract roles from resource_access for the current client
        var keycloakAuthenticationProvider = new KeycloakResourceAuthenticationProvider(clientName);


        // # Add "ROLE_" before every role in the token
        // keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());

        // # Use roles as they are (no prefix)
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new NullAuthoritiesMapper());

        auth.authenticationProvider(keycloakAuthenticationProvider);
    }


    @Bean
    public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and()
            .csrf().disable()
            .authorizeRequests().anyRequest().hasRole("MICRO_ADMIN").and()

        ;
        super.configure(http);
    }

}
