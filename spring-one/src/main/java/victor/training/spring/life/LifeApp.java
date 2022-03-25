package victor.training.spring.life;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

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
@Slf4j
@Service
//abstract
class OrderExporter  {
//	@Autowired
//	private LabelService labelService;

	// bad practice because it allows you fetch other things
//	@Autowired
//	private ApplicationContext applicationContext;

	// more focused dependencies << USE THIS
	@Autowired
	private ObjectFactory<LabelService> labelServiceObjectFactory;

//	@Lookup
//	public abstract LabelService getLabelService() ; // don't use, too magic !

	public void export(Locale locale) {
		log.debug("Running export in " + locale);
		LabelService labelService = labelServiceObjectFactory.getObject();  // ask for one
//		LabelService labelService = getLabelService();  // ask for one
		labelService.load(locale);
		log.debug("Origin Country: " + labelService.getCountryName("rO")); 
	}
}

// SINGLETONS SHALL NOT HAVE STATE related to current request
// >> because then a RACE CONDITION will appear in a multithredead env (like handlin Web requests)

@Service
@Scope(value = "prototype"/*proxyMode = ScopedProxyMode.TARGET_CLASS*/) // spring will create a NEW instance every time it needs to give you one.
//other lifecycles than the defaulT(singleton):
// - prototype : you need multiple instances of the same type
// - request : one instance exists PER HTTP REQUEST
// - session: one instance exists PER HTTP SESSION, but REST is stateless.
class LabelService {
	private static final Logger log = LoggerFactory.getLogger(LabelService.class);
	private final CountryRepo countryRepo; // pointing to another stateLESS class is NOT state.

	public LabelService(CountryRepo countryRepo) {
		log.debug(this + ".new()");
		this.countryRepo = countryRepo;
	}

	private Map<String, String> countryNames; // state in singleton

//	@PostConstruct
	public void load(Locale locale) {
		log.debug(this + ".load()");
		countryNames = countryRepo.loadCountryNamesAsMap(locale);
	}
	
	public String getCountryName(String iso2Code) {
		log.debug(this + ".getCountryName()");
		return countryNames.get(iso2Code.toUpperCase());
	}
	public String toString() {
		return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
	}
}