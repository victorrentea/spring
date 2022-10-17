package victor.training.spring.web.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
class SecurityConfigKeyCloak extends KeycloakWebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, @Value("${keycloak.resource}") String clientName) {
        var keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        // C+B1) Client-specific roles
//        var keycloakAuthenticationProvider = new KeycloakResourceAuthenticationProvider(clientName);

        // A) Role-based security : prefix every role in the token with "ROLE_"
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());

        // B) Authority-based security
        // B1) Extracting fine-grained authorities from Access Token (relies on KeyCloak composite Roles)
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new NullAuthoritiesMapper());

        // B2) converting ROLE from token into local authorities (eg via a local enum)
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new RolesFromTokenToLocalAuthorities());

        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    // Specifies the session authentication strategy
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .mvcMatchers("/spa/**", "/api/**").authenticated()
                .mvcMatchers("/sso/**").permitAll()
                .anyRequest().permitAll()
        ;
    }

    // needed to secure /spa/** but to leave /sso/** unsecured.
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/spa"); // entering :8080 in bro-> redirect to :8080/spa
        registry.addViewController("/spa").setViewName("forward:/index.html"); // :8080/spa is served index.html
    }
}
