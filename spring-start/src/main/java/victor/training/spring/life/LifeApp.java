package victor.training.spring.life;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
		//imagine 2 concurrent requests
		new Thread(()->exporter.export(Locale.ENGLISH)).start();
		new Thread(()->exporter.export(Locale.FRENCH)).start();

	}
}
@RequiredArgsConstructor
@Slf4j
@Service
class OrderExporter  {
	private final InvoiceExporter invoiceExporter;
//	private final LabelService labelService;
	private final ApplicationContext applicationContext;
	@SneakyThrows
	public void export(Locale locale) {
		LabelService labelService = applicationContext.getBean(LabelService.class);
		labelService.load(locale);
		log.info("Running export in " + locale);
		log.info("Origin Country: " + labelService.getCountryName("rO"));
		Thread.sleep(100);
		invoiceExporter.exportInvoice(labelService);
	}
}
@RequiredArgsConstructor
@Slf4j
@Service
class InvoiceExporter {
	// NEVER INJECT prototype scoped in a Singleton scoped. => the instance effectively becomes singleton itself.
//	private final LabelService labelService; // is injected ONCE only,because InvoiceExporter is a singleton
	public void exportInvoice(LabelService labelService) {
		log.info("Invoice Country: " + labelService.getCountryName("ES"));
	}
}
@RequiredArgsConstructor // only injects final fields
@Slf4j
@Service
//@Scope("singleton") // 1 instance / app [default]
@Scope("prototype") // 1 NEW instance every time Spring is asked for one/needs one
class LabelService {
	private final CountryRepo countryRepo;
	private Map<String, String> countryNames; // mutable state in a singleton can get you fired.

	public void load(Locale locale) {
		log.info(this + ".load()");
		countryNames = countryRepo.loadCountryNamesAsMap(locale);
	}

	public String getCountryName(String iso2Code) {
		log.info(this + ".getCountryName()");
		return countryNames.get(iso2Code.toUpperCase());
	}
	public String toString() {
		return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
	}
}

//@Component
//class PerfectDesign {
//	// solution1: state kept in a field of a singleton, initialzied just at startup
//	private Map<Locale,Map<String, String>> countryNames;
//
//	// solution2: caching the data
//	@Cacheable("country-name")
//	public Map<String, String> getIsoCodeToCountryNameMap(Locale locale) {
//
//	}
//}


@RequiredArgsConstructor
@Slf4j
@Service
class ShouldCrash {
//	private final LabelService labelService;
}