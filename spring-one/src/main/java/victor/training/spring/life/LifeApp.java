package victor.training.spring.life;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class LifeApp implements CommandLineRunner{
	@Bean
	public static CustomScopeConfigurer defineThreadScope() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		// WARNING: Leaks memory. Prefer 'request' scope or read here: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/SimpleThreadScope.html
		configurer.addScope("thread", new SimpleThreadScope());
		return configurer;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LifeApp.class, args);
	}
	
	@Autowired 
	private OrderExporter exporter;
	
	// TODO [1] make singleton; multi-thread + mutable state = BAD
	// TODO [2] instantiate manually, set dependencies, pass around; no AOP
	// TODO [3] prototype scope + ObjectFactory or @Lookup. Did you said "Factory"? ...
	// TODO [4] thread/request scope. HOW it works?! Leaks: @see SimpleThreadScope javadoc

	public void run(String... args) {
		new Thread(() ->exporter.export(Locale.ENGLISH)).start();
		new Thread(() ->exporter.export(Locale.FRENCH)).start();
		
	}
}

@Component
class LabelServiceFactory {
	private final CountryRepo countryRepo;
	// daca sunt multe dependinte sau vreau sa mockuiesc LabelService cand testez OrderExporter

	LabelServiceFactory(CountryRepo countryRepo) {
		this.countryRepo = countryRepo;
	}

	public LabelService createLabelService(Locale locale) {
		LabelService labelService = new LabelService(countryRepo);
		labelService.load(locale);
		return labelService;
	}

}

@Service
class OrderExporter  {
	private static final Logger log = LoggerFactory.getLogger(OrderExporter.class);
	private final InvoiceExporter invoiceExporter;
	private  final LabelServiceFactory labelServiceFactory;

	public OrderExporter(InvoiceExporter invoiceExporter, LabelServiceFactory labelServiceFactory) {
		this.invoiceExporter = invoiceExporter;
		this.labelServiceFactory = labelServiceFactory;
	}

	public void export(Locale locale) {
		log.debug("Running export in " + locale);

		LabelService labelService = labelServiceFactory.createLabelService(locale);
		log.debug("Origin Country: " + labelService.getCountryName("rO"));
		invoiceExporter.exportInvoice(labelService);
	}
}
@Service
class InvoiceExporter {
	private static final Logger log = LoggerFactory.getLogger(InvoiceExporter.class);

	public void exportInvoice(LabelService labelService) {
		log.debug("Invoice Country: " + labelService.getCountryName("ES"));
	}
}

class LabelService {
	private static final Logger log = LoggerFactory.getLogger(LabelService.class);
	private final CountryRepo countryRepo;// +6 de la nasu mare
	
	public LabelService(CountryRepo countryRepo) {
		System.out.println("+1 Label Service: " + this.hashCode());
		this.countryRepo = countryRepo;
	}

	private Map<String, String> countryNames; // crapa sonar https://rules.sonarsource.com/java/RSPEC-3749?search=injected

	public void load(Locale locale) {
		log.debug("LabelService.load() on instance " + this.hashCode());
		countryNames = countryRepo.loadCountryNamesAsMap(locale);
	}
	
	public String getCountryName(String iso2Code) {
		log.debug("LabelService.getCountryName() on instance " + this.hashCode());
		return countryNames.get(iso2Code.toUpperCase());
	}
}