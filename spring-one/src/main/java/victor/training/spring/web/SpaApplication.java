package victor.training.spring.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
@EnableAsync
@EnableCaching
@EnableSwagger2
@SpringBootApplication
public class SpaApplication {

	public static void main(String[] args) {

		// 2-SSL : Mutual Authentication

//		SSLContext sslContext;
//		SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//		sslSocketFactory.createSocket()

		//



		new SpringApplicationBuilder(SpaApplication.class)
				.profiles("spa")
				.run(args);
	}

}
