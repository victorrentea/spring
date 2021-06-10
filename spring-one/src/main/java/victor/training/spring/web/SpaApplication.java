package victor.training.spring.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@EnableSwagger2
@SpringBootApplication
public class SpaApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpaApplication.class).run(args);
	}

}
