package victor.training.spring.life;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.Map;

// bazele unei extensii la spring company-wide. mm-commons-0.1.jar
@Configuration
@ConditionalOnProperty(name = "pace", havingValue = "true",matchIfMissing = true) // ~ @Profile
class MyAutoConfig {
	@PostConstruct
	public void method() {
		System.out.println("E PACE!");
	}
}

@SpringBootApplication // 1
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
	

	
	// TODO [1] make singleton; multi-thread + mutable state = BAD
	// TODO [2] instantiate manually, set dependencies, pass around; no AOP
	// TODO [3] prototype scope + ObjectFactory or @Lookup. Did you said "Factory"? ...
	// TODO [4] thread/request scope. HOW it works?! Leaks: @see SimpleThreadScope javadoc

	public void run(String... args) {
		exporter.export(Locale.ENGLISH);
		// TODO exporter.export(Locale.FRENCH);

		// ASA SE FOLOSESTE CORECT UN SCOPE PROTOTYPE: II CERI REPETAT LUI SPRING INSTANTE
		OrderExporter exporter1 = applicationContext.getBean(OrderExporter.class);
		System.out.println(exporter1);
		OrderExporter exporter2 = applicationContext.getBean(OrderExporter.class);
		System.out.println(exporter2);

		// DE CE SA EVITATI PROTOTYPE:
		// 1. E STATEFUL -> GREU DE MENTINUT
		// 2. TZAPA CAND INJECTEZI UN PROTO INTR-UN SINGLETON -> INJECTIA SE FACE O SINGURA DATA -> 1 SINGURA INSTANTA DE PROTO VA FI CREATA
	}

	@Autowired
	private OrderExporter exporter; // DACA ASTA E PROTOTYPE => ITI IEI TZAPA

	@Autowired
	private ApplicationContext applicationContext;
}
@Slf4j
@Service
//@Scope("singleton") // default 1 instanta creata  / clasa
//@Scope("prototype") // ori de cate ori spring are nevoie de o instanta, va creea una noua
//@Scope("request") // 1 instanta / http request
class OrderExporter  {
	// date pe campuri!?! = stateful = BAD!
	private String currentUsername;
	private String userLocale;
	private String shoppingCart ;



	@Autowired
	private InvoiceExporter invoiceExporter;
	@Autowired
	private LabelService labelService;

	public void export(Locale locale) {
		log.debug("Running export in " + locale);
		log.debug("Origin Country: " + labelService.getCountryName("rO"));
		invoiceExporter.exportInvoice();
	}
}
@Slf4j
@Service
class InvoiceExporter {
	@Autowired
	private LabelService labelService;
	
	public void exportInvoice() {
		log.debug("Invoice Country: " + labelService.getCountryName("ES"));
	}
}

@Slf4j
@Service
class LabelService {
	private final CountryRepo countryRepo;
	
	public LabelService(CountryRepo countryRepo) {
		log.debug(this + ".new()");
		this.countryRepo = countryRepo;
	}

	private Map<String, String> countryNames;

	@PostConstruct
	public void load() {
		log.debug(this + ".load()");
		countryNames = countryRepo.loadCountryNamesAsMap(Locale.ENGLISH);
	}
	
	public String getCountryName(String iso2Code) {
		log.debug(this + ".getCountryName()");
		return countryNames.get(iso2Code.toUpperCase());
	}
	public String toString() {
		return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
	}
}