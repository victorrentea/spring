package victor.training.spring;

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


//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/api/**")
//					 .allowCredentials(true) // allows receiving session cookie
////					 .allowedOriginPatterns("http://localhost:8081") // NodeJS
//					 .allowedOriginPatterns("http://*") // Too broad
//            ;
//                // also don't forget to add .cors() to spring security
//			}
//		};
//	}

}
