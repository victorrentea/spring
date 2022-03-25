package victor.training.spring.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import victor.training.spring.web.controller.util.TestDBConnection;

@EnableCaching
@EnableSwagger2
@SpringBootApplication
public class SpaApplication {

//   @Bean
//   public int bang() {
//      return 1;
//   }

   public static void main(String[] args) {
         new SpringApplicationBuilder(SpaApplication.class)
             .listeners(new TestDBConnection())
             .profiles("spa") // re-enables WEB nature (disabled in application.properties for the other apps not to start :8080)
             .run(args);
   }


//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/api/**")
//					 .allowCredentials(true) // also don't forget to add .cors() to spring security
//					 .allowedOriginPatterns("http://localhost:9999*");
//			}
//		};
//	}

}
