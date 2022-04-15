package victor.training.spring.web;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyAuthoritiesMapper;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import victor.training.spring.web.entity.UserRole;
import victor.training.spring.web.security.KeycloakAuthenticationProviderExtractingClientRoles;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
class SecurityConfigKeyCloak extends KeycloakWebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Value("${keycloak.resource}")
    String keycloakClientName;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        KeycloakAuthenticationProvider keycloakAuthenticationProvider = new KeycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        // Resolve ROLES received to fine-grained authorities
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new RoleHierarchyAuthoritiesMapper(SecurityConfigKeyCloak::resolveAuthoritiesByRoles));
//        auth.authenticationProvider(keycloakAuthenticationProvider);

        // USE client-scoped roles (authorities from KC)
        auth.authenticationProvider(new KeycloakAuthenticationProviderExtractingClientRoles(keycloakClientName));
    }

    public static List<GrantedAuthority> resolveAuthoritiesByRoles(Collection<? extends GrantedAuthority> authorities) {
        List<String> featureAuthorities = authorities.stream()
            .flatMap(roleName -> UserRole.valueOf(roleName.getAuthority()).getAuthorities().stream())
            .collect(toList());
        return Stream.concat(authorities.stream(), featureAuthorities.stream().map(SimpleGrantedAuthority::new)).collect(toList());
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
            .cors().and()
            .csrf().disable()
            .authorizeRequests()
            .mvcMatchers("/spa/**", "/api/**").authenticated()
            .mvcMatchers("/sso/**").permitAll()
            .anyRequest().permitAll()

            // saves memory :
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER) // if you authenticate every request in isolation based on tokens >> you don't need session
        ;
    }

    // needed to secure /spa/** but to leave /sso/** unsecured.
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/","/spa"); // entering :8080 in bro-> redirect to :8080/spa
        registry.addViewController("/spa").setViewName("forward:/index.html"); // :8080/spa is served index.html
    }
}
