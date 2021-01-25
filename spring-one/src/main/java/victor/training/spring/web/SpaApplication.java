package victor.training.spring.web;

import org.infinispan.spring.starter.remote.InfinispanRemoteConfigurer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import victor.training.spring.props.PropertiesApp;

@EnableAsync
@EnableSwagger2
@SpringBootApplication
@EnableCaching
@Import(PropertiesApp.class)
public class SpaApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpaApplication.class)
				.profiles("spa")
				.run(args);
	}

//	@Bean
//	public org.infinispan.client.hotrod.configuration.Configuration customConfiguration() {
//		return new ConfigurationBuilder()
//			.addServer()
//			.host("127.0.0.1")
//			.port(12345)
//			.build();
//	}
}
