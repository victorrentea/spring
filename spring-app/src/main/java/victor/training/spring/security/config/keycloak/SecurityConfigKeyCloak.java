package victor.training.spring.security.config.keycloak;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;

@Profile("keycloak")
@EnableWebSecurity // (debug = true) // see the filter chain in use
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
class SecurityConfigKeyCloak extends KeycloakWebSecurityConfigurerAdapter implements WebMvcConfigurer {


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, @Value("${keycloak.resource}") String clientName) {
        // extract REALM GLOBAL roles from realm_access
         var keycloakAuthenticationProvider = new KeycloakAuthenticationProvider();

        // extract APPLICATION-SCOPED roles from resource_access for the current client(=app)
//        var keycloakAuthenticationProvider = new KeycloakResourceAuthenticationProvider(clientName);


        // # Add "ROLE_" before every role in the token
         keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());

        // # Use roles as they are (no prefix added)
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new NullAuthoritiesMapper());

        // # Convert ROLE from token into local authorities via a local enum
        // keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new RolesFromTokenToLocalAuthorities());

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
        http.requestMatcher(request -> !EndpointRequest.toAnyEndpoint().matches(request)); // securing /actuator separately from the app

        super.configure(http); // critical, let KC configure its defaults

        http.csrf().disable(); // OK since I never take <form> POSTs

        // http.cors(); // needed only if .js files are served by a CDN (eg)

        http.authorizeRequests()
                .mvcMatchers("/sso/**").permitAll()
                .anyRequest().access("isAuthenticated() && !hasAuthority('ACTUATOR')");
                // credentials to /actuator cannot target application apis
    }



    // When mixing keycloak with basic authentication for /actuator, some keycloak components are too eager to register themselves
    @Bean
    public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(KeycloakAuthenticationProcessingFilter filter) {
        // necessary due to http://www.keycloak.org/docs/latest/securing_apps/index.html#avoid-double-filter-bean-registration
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(KeycloakPreAuthActionsFilter filter) {
        // necessary due to http://www.keycloak.org/docs/latest/securing_apps/index.html#avoid-double-filter-bean-registration
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    // needed to secure /spa/** but to leave /sso/** unsecured.
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/spa"); // entering :8080 in bro-> redirect to :8080/spa -> login->Oauth
        registry.addViewController("/spa").setViewName("forward:/index.html"); // :8080/spa serves served index.html
        registry.addViewController("/logout").setViewName("redirect:/sso/logout");
    }
}
