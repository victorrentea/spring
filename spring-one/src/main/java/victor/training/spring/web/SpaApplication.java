package victor.training.spring.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import victor.training.spring.web.controller.util.TestDBConnection;

@EnableCaching
@EnableSwagger2
@SpringBootApplication
public class SpaApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpaApplication.class)
				.listeners(new TestDBConnection())
				.profiles("spa") // re-enables WEB nature (disabled in application.properties for the other apps not to start :8080)
				.run(args);
	}


}
//
//@Service
//class MyService {
//	private boolean cr323;
//
//}
