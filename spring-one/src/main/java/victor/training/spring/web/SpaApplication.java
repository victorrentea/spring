package victor.training.spring.web;

import io.micrometer.core.aop.TimedAspect;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class SpaApplication {

	@Bean
	public TimedAspect timedAspect() {
		return new TimedAspect(meterRegistry);
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpaApplication.class)
				.profiles("spa") // re-enables WEB nature (disabled in application.properties for the other apps)
				.run(args);
	}

}
//
//@Service
//class MyService {
//	private boolean cr323;
//
//}
