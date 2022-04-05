package victor.training.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import victor.training.spring.web.controller.util.TestDBConnectionInitializer;

@EnableCaching
@EnableSwagger2
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
//					 .allowCredentials(true) // also don't forget to add .cors() to spring security
//					 .allowedOriginPatterns("http://localhost:9999*");
//			}
//		};
//	}

}
