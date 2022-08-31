package victor.training.spring.life;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class LifeApp implements CommandLineRunner{
	@Bean
	public static CustomScopeConfigurer defineThreadScope() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		// WARNING: Can leaks memory if data remains on thread. Prefer 'request' scope or read here: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/SimpleThreadScope.html
		// TODO finally { ClearableThreadScope.clearAllThreadData(); }
		configurer.addScope("thread", new ClearableThreadScope());
		return configurer;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LifeApp.class);
	}
	
	@Autowired 
	private OrderExporter exporter;
	
	// TODO [1] make singleton; multi-thread + mutable state = BAD
	// TODO [2] instantiate manually, set dependencies, pass around; no AOP
	// TODO [3] prototype scope + ObjectFactory or @Lookup. Did you said "Factory"? ...
	// TODO [4] thread/request scope. HOW it works?! Leaks: @see SimpleThreadScope javadoc

	public void run(String... args) {
		new Thread(()->exporter.export(Locale.ENGLISH)).start();
		new Thread(()->exporter.export(Locale.FRENCH)).start();
	}
}
@RequiredArgsConstructor
@Slf4j
@Service
class OrderExporter  {
	private final LabelService labelService;

	public void export(Locale locale) {
		log.info("Running export in " + locale);
		log.info("Origin Country: " + labelService.getCountryName(locale, "rO"));
	}
}

@RequiredArgsConstructor
@Slf4j
@Service
class LabelService {
	private final CountryRepo countryRepo;

	private final Map<Locale, Map<String, String>> countryNames = new HashMap<>(); // shared mutable data in a singleton, + multithread = RIP

	@PostConstruct
	public void load() {
		log.info(this + ".load()");
		for (Locale locale : List.of(Locale.ENGLISH, Locale.FRENCH)) {
			countryNames.put(locale, countryRepo.loadCountryNamesAsMap(locale));
		}
	}
	
	public String getCountryName(Locale locale, String iso2Code) {
		log.info(this + ".getCountryName()");
		return countryNames.get(locale).get(iso2Code.toUpperCase());
	}
	public String toString() {
		return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
	}
}