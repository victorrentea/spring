package victor.training.spring.security.config.keycloak;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpMethod.DELETE;

@Profile("keycloak")
@EnableWebSecurity  // (debug = true) // see the filter chain in use
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
class SecurityConfigKeyCloak extends KeycloakWebSecurityConfigurerAdapter implements WebMvcConfigurer {


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, @Value("${keycloak.resource}") String clientName) {
        // extract roles from realm_access
         var keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        // extract roles from resource_access for the current client
//        var keycloakAuthenticationProvider = new KeycloakResourceAuthenticationProvider(clientName);


        // # Add "ROLE_" before every role in the token
        // keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());

        // # Use roles as they are (no prefix)
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new NullAuthoritiesMapper());

        // # Convert ROLE from token into local authorities via a local enum
//         keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new RolesFromTokenToLocalAuthorities());

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
        super.configure(http); // critical, defer to KC a lot of work

        http.csrf().disable(); // OK since I never take <form> POSTs

        // http.cors(); // needed only if .js files are served by a CDN (eg)

        http.authorizeRequests()
//                .mvcMatchers(DELETE, "api/trainings/*").hasAuthority("training.delete")
                .mvcMatchers("/spa/**", "/api/**").authenticated()
                .mvcMatchers("/sso/**").permitAll()
                .anyRequest().permitAll();
    }

    // needed to secure /spa/** but to leave /sso/** unsecured.
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/spa"); // entering :8080 in bro-> redirect to :8080/spa
        registry.addViewController("/spa").setViewName("forward:/index.html"); // :8080/spa is served index.html
    }
}
