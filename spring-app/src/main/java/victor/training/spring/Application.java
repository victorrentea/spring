package victor.training.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import victor.training.spring.web.controller.util.TestDBConnectionInitializer;

@EnableCaching
@EnableAspectJAutoProxy(exposeProxy = true) // allow to use AopContext.currentProxy()
@SpringBootApplication
public class Application {

   public static void main(String[] args) {
      new SpringApplicationBuilder(Application.class)
          .listeners(new TestDBConnectionInitializer())
          .run(args);
   }

   @Bean
   public WebMvcConfigurer corsConfigurer(@Value("${cdn.host:http://localhost:8081}") String cdnHost) {
      return new WebMvcConfigurer() {
         @Override
         public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**")
//                .allowedOriginPatterns("*") // too broad, and any JS from any malicious website out
                // there can now fire AJAX calls to all your REST APIs

                .allowedOriginPatterns(cdnHost)
                .allowCredentials(true) // allows receiving session cookie
            ;
         }
      };
   }
}
