//package victor.training.spring.web;
//
//import lombok.extern.slf4j.Slf4j;
//import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
//import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
//import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
//import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.access.hierarchicalroles.RoleHierarchyAuthoritiesMapper;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
//import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
//import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import victor.training.spring.web.entity.UserRole;
//import victor.training.spring.web.security.InspectingFilter;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Enumeration;
//import java.util.List;
//import java.util.stream.Stream;
//
//import static java.util.stream.Collectors.toList;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) // activez proxierea metodelor cu @PreAuthorized
//@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
//class SecurityConfigKeyCloak extends KeycloakWebSecurityConfigurerAdapter implements WebMvcConfigurer {
//    // Submits the KeycloakAuthenticationProvider to the AuthenticationManager
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
//        // 1) user roles -> principal authorities
////        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
//
//        // 2) user ROLES -> resolve locally through a Map<role,List<authorities>>
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new RoleHierarchyAuthoritiesMapper(SecurityConfigKeyCloak::resolveAuthoritiesByRoles));
//        auth.authenticationProvider(keycloakAuthenticationProvider);
//
//        // 3) [pro] USE client-scoped roles (application-specific)
//        //  auth.authenticationProvider(new KeycloakAuthenticationProviderExtractingClientRoles(keycloakClientName));
//    }
//
//    @Bean
//    public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
//        return new KeycloakSpringBootConfigResolver();
//    }
//
//    // Specifies the session authentication strategy
//    @Bean
//    @Override
//    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
//    }
//
//    public static List<GrantedAuthority> resolveAuthoritiesByRoles(Collection<? extends GrantedAuthority> roles) {
//        List<String> featureAuthorities = roles.stream()
//            .flatMap(roleName -> {
//                try {
//                    return UserRole.valueOf(roleName.getAuthority()).getAuthorities().stream();
//                } catch (IllegalArgumentException e) {
//                    return Stream.of();
//                }
//            })
//            .collect(toList());
//        return Stream.concat(roles.stream(), featureAuthorities.stream().map(SimpleGrantedAuthority::new)).collect(toList());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        http
//            .csrf().disable() // ok daca niciodata nu faci <form> <input type="submit"> catre server
//            .addFilterBefore(new InspectingFilter(), BasicAuthenticationFilter.class)
//            .authorizeRequests()
////                .mvcMatchers(HttpMethod.DELETE, "/api/trainings/*").hasRole("ADMIN")
//                .mvcMatchers("/spa/**", "/api/**").authenticated()
////                .mvcMatchers("/sso/**").permitAll()
//                .anyRequest().permitAll()
//            .and().sessionManagement().disable()
//        ;
//    }
//
//    // needed to secure /spa/** but to leave /sso/** unsecured.
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addRedirectViewController("/","/spa"); // entering :8080 in bro-> redirect to :8080/spa
//        registry.addViewController("/spa").setViewName("forward:/index.html"); // :8080/spa is served index.html
//    }
//}
//
//
