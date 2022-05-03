package victor.training.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@SpringBootApplication
public class SpringAppCdn implements WebMvcConfigurer {
   public static void main(String[] args) {
       SpringApplication.run(SpringAppCdn.class, args);
   }

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      File staticFolder = new File("spring-app/src/main/resources/static");
      if (!staticFolder.isDirectory()) {
         throw new IllegalArgumentException("Folder to show static files from, does not exist:" + staticFolder.getAbsolutePath());
      }
      registry.addResourceHandler("/**").addResourceLocations(staticFolder.toURI().toString());
   }
}