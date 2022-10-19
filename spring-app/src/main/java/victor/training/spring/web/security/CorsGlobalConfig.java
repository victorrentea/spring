//package victor.training.spring.web.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsGlobalConfig {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/api/**")
//                        .allowedMethods("*")
//                        .allowCredentials(true) // allows receiving session cookie (if using cookies)
//                            // daca autorizezi req de la Bro la Be prin tokeni pe headeri, nu ai nevoie de asta true (cookie)
//                        .allowedOriginPatterns("http://localhost:8081") // eg NodeJS / apache
////					 .allowedOriginPatterns("http://*") // Too broad
//                ;
//                // also don't forget to add .cors() to spring security
//            }
//        };
//    }
//}
