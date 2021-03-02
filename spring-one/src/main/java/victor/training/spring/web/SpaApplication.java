package victor.training.spring.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.export.MBeanExporter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
//@EnableSwagger2
@EnableMBeanExport
@SpringBootApplication
public class SpaApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpaApplication.class)
				.profiles("spa")
				.run(args);
	}


}
