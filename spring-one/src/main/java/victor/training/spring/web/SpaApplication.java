package victor.training.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import victor.training.spring.props.PropertiesApp;
import victor.training.spring.props.WelcomeInfo;

import javax.annotation.PostConstruct;

@EnableSwagger2
@EnableCaching
@EnableAsync
@SpringBootApplication
@Import(PropertiesApp.class)
public class SpaApplication implements CommandLineRunner {
	@Autowired
	Viata viata;


	public static void main(String[] args) {
//		if( sunt pe win) -> activeprofiles+="local"
		new SpringApplicationBuilder(SpaApplication.class)
				.profiles("spa")
				.run(args);
	}

	@PostConstruct
	public void init() {
		viata.traiste();
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
interface Viata {
	void traiste();
}

@Component
//@Profile("!local")
class ViataZen implements Viata {
	public void traiste() {
		System.out.println("ZEN");
		// real men test in production
	}
}
@Component
@Primary
@Profile("local")
class ViataDeDev implements Viata {
	public void traiste() {
		System.out.println("S3");
	}
}