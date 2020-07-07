package victor.training.spring.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import victor.training.spring.async.AsyncApp;
import victor.training.spring.props.PropertiesApp;

@EnableCaching
@EnableSwagger2
@SpringBootApplication
@Import({DBConfig.class, AsyncApp.class, PropertiesApp.class})
public class SpaApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpaApplication.class)
				.profiles("spa")
				.run(args);
	}
}
