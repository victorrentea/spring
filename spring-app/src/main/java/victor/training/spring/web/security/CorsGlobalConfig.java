package victor.training.spring.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsGlobalConfig {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            // allow anyone to call my endpoints invisibly (AJAX) if the user logged in my app anytime during the last 30 min
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/api/**")
//                        .allowedMethods("*")
//                        .allowCredentials(true) // allows receiving session cookie (if using cookies)
//                        .allowedOriginPatterns("http://cdn.myapp.com") // OK
//                        .allowedOriginPatterns("http://*") // Too broad
////                        .allowedOriginPatterns("http://localhost:8081") // eg NodeJS
//                ;
//                // also don't forget to add .cors() to spring security
//            }
//        };
//    }
}
