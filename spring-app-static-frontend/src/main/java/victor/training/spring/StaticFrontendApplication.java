package victor.training.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@SpringBootApplication
public class StaticFrontendApplication  implements WebMvcConfigurer {
   public static void main(String[] args) {
       SpringApplication.run(StaticFrontendApplication.class, args);
   }

//}
//@Profile("dev-machine")
//class OnMyLocalConfig implements WebMvcConfigurer {

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      File staticFolder = new File("spring-app/src/main/resources/static");
      if (!staticFolder.isDirectory()) {
         System.err.println("Folder does not exist:" + staticFolder.getAbsolutePath());
      }
      registry.addResourceHandler("/**").addResourceLocations(staticFolder.toURI().toString());
   }
}