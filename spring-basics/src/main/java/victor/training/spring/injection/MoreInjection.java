package victor.training.spring.injection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.stereotype.Service;
import victor.training.spring.life.CountryRepo;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.Map;

@SpringBootApplication
public class MoreInjection implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MoreInjection.class);
	}

	@Qualifier("AShmecher")
	@Autowired
	private I i;
	@Override
	public void run(String... args) throws Exception {

	}
}

interface I {}

@Service
class AShmecher implements I{}
@Service
class B implements I{}