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
		new Thread(()->exporter.export(Locale.ENGLISH)).start();
		new Thread(()->exporter.export(Locale.FRENCH)).start();
		
	}
}
@RequiredArgsConstructor
@Slf4j
@Service
class OrderExporter  {
	private final InvoiceExporter invoiceExporter; // deploy time failure if missing
//	private final LabelService labelService; // 1
	private final ApplicationContext applicationContext;

	public void export(Locale locale) {
		LabelService labelService = applicationContext.getBean(LabelService.class);
		log.debug("Running export in " + locale);
		labelService.load(locale);
		log.debug("Origin Country: " + labelService.getCountryName("rO"));
		// 1: hidden in the code. dep is not obvious anymore at the class
		// 2: risky to do getBean(class) instead of @Autowired dependency because you might get a runtime ex if the dep is not defined.
//		InvoiceExporter invoiceExporter = applicationContext.getBean(InvoiceExporter.class);
		invoiceExporter.exportInvoice(labelService);
	}
}
@RequiredArgsConstructor
@Slf4j
@Service
class InvoiceExporter {
//	private final LabelService labelService; // 2

	public void exportInvoice(LabelService labelService) {
		log.debug("Invoice Country: " + labelService.getCountryName("ES"));
	}
}

@Slf4j
@Service
@Scope("prototype") // >> every time this bean is requested, a new instance is created
//@Scope("request") // lives as long as the handling of a HTTP Request
//@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS) // if there are 3 users logged in> you have 3 objects like this in memory
class LabelService {
	private final CountryRepo countryRepo;
	private Map<String, String> countryNames;

	public LabelService(CountryRepo countryRepo) {
		log.debug(this + ".new()");
		this.countryRepo = countryRepo;
	}


	public void load(Locale locale) {
		log.debug(this + ".load()");
		// NEVER store request-specific data in a field of a singleton
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