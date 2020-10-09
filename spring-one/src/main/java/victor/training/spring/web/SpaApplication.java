package victor.training.spring.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import victor.training.spring.web.controller.CustomHeaderInterceptor;

import java.time.Clock;

@Slf4j
@EnableSwagger2
@EnableCaching
@EnableAsync
@SpringBootApplication
public class SpaApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpaApplication.class)
				.profiles("spa")
				.run(args);
	}

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(headShotInterceptor()).addPathPatterns("/**");
	}
	@Bean
	public HandlerInterceptor headShotInterceptor() {
		return new CustomHeaderInterceptor();
	}

	@Autowired
	public void printDatabaseUrl(@Value("${spring.datasource.url}") String dbUrl) {
		log.info("Using database: {} <<<", dbUrl);
	}

	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}

	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}
}
