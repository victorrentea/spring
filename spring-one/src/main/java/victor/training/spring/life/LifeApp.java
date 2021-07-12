package victor.training.spring.life;

import lombok.extern.slf4j.Slf4j;
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
	
	public void run(String... args) {
		new Thread(() -> exporter.export(Locale.ENGLISH)).start();
		new Thread(() -> exporter.export(Locale.FRENCH)).start();
	}
}
@Slf4j
@Service
class OrderExporter  { // 1 instanta !
	@Autowired
	private InvoiceExporter invoiceExporter;
	@Autowired
	private LabelService labelService;

	public void export(Locale locale) {
		log.debug("Running export in " + locale);
		labelService.load(locale);
		log.debug("Origin Country: " + labelService.getCountryName("rO"));
		invoiceExporter.exportInvoice();
	}
}
@Slf4j
@Service 
class InvoiceExporter { // 1
	@Autowired
	private LabelService labelService;
	public void exportInvoice() {
		log.debug("Invoice Country: " + labelService.getCountryName("ES"));
	}
}

@Slf4j
@Service
@Scope("prototype")
class LabelService { // 1
	private final CountryRepo countryRepo;
	
	public LabelService(CountryRepo countryRepo) {
		System.out.println("+1 Label Service: " + this.hashCode());
		this.countryRepo = countryRepo;
	}

	private Map<String, String> countryNames;

	public void load(Locale locale) {
		log.debug("LabelService.load() on instance " + this.hashCode());
		countryNames = countryRepo.loadCountryNamesAsMap(locale);
	}

	public String getCountryName(String iso2Code) {
		log.debug("LabelService.getCountryName() on instance " + this.hashCode());
		return countryNames.get(iso2Code.toUpperCase());
	}
}