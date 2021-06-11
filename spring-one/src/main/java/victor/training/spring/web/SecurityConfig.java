package victor.training.spring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("local")
 class SecurityConfigPtLocal  extends WebSecurityConfigurerAdapter /*implements WebMvcConfigurer */{

}

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("!local")
public class SecurityConfig  extends WebSecurityConfigurerAdapter /*implements WebMvcConfigurer */{

    // TODO [SEC] Start with ROLE-based authorization on URL-patterns

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
////        registry.addMapping("http://localhost:8080");
////        registry.addMapping("http://api.coface.com"); // aici deployezi tu
//        registry.addMapping("/api/**").allowedOrigins("http://cdn.coface.com").allowCredentials(true);
//        registry.addMapping("/api/**").allowedOrigins("http://127.0.0.1:9999").allowCredentials(true);
//    }
//@Bean
//public CorsConfigurationSource corsConfigurationSource() {
//    return new CorsConfigurationSource() {
//        @Override
//        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//            CorsConfiguration config = new CorsConfiguration();
//            config.addAllowedOrigin("http://127.0.0.1:9999");
//            config.setAllowCredentials(true);
//            return config;
//        }
//    };
//}



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {



        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:9999"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "content-type", "x-requested-with",
            "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "x-auth-token", "x-app-id", "Origin",
            "Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers",
            "Authorization", "Cache-Control"));
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//HttpServletRequest request;
//request.getSession().invalidate();
       http.cors().and()//.//cors().configurationSource().and()
//           .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()

           .csrf().disable()

            .authorizeRequests()
               .anyRequest().authenticated()
       .and()
           .httpBasic()
//       .formLogin().permitAll()
            ;
    }

    // *** Dummy users 100% in-mem
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build();
        UserDetails adminDetails = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(userDetails, adminDetails);
    }

    // ... then, switch to loading user data from DB:
    // *** Also loading data from DB
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new DatabaseUserDetailsService();
//    }

}
