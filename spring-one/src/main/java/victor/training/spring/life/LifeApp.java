package victor.training.spring.life;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
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
//		exporter.export(Locale.ENGLISH);
//		exporter.export(Locale.FRENCH);
		new Thread(()->exporter.export(Locale.ENGLISH)).start();
		new Thread(()->exporter.export(Locale.FRENCH)).start();
	}
}
@RequiredArgsConstructor
@Slf4j
@Service
class OrderExporter  {
	private final InvoiceExporter invoiceExporter;
	private final ApplicationContext applicationContext;

	public void export(Locale locale) {
		LabelService labelService = applicationContext.getBean(LabelService.class);
		log.debug("Running export in " + locale);
		labelService.load(locale);
		log.debug("Origin Country: " + labelService.getCountryName("rO"));
		invoiceExporter.exportInvoice(labelService);
	}
}
@RequiredArgsConstructor
@Slf4j
@Service
class InvoiceExporter {
	public void exportInvoice(LabelService labelService) {
		log.debug("Invoice Country: " + labelService.getCountryName("ES"));
	}
}

@Slf4j
@Service
@Scope("prototype") // tells spring to create a new instance *every time it needs one*
class LabelService {
	private final CountryRepo countryRepo;

	public LabelService(CountryRepo countryRepo) {
		log.debug(this + ".new()");
		this.countryRepo = countryRepo;
	}

	private Map<String, String> countryNames; // in a singleton, never keep request-specific data

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