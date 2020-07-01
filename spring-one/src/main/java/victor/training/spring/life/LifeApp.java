package victor.training.spring.life;

import java.util.Locale;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootApplication
public class LifeApp implements CommandLineRunner {
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


	private TransactionTemplate txTemplate;

	@Autowired
	public void initTxTemplate(PlatformTransactionManager transactionManager) {
		txTemplate = new TransactionTemplate(transactionManager);
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		// in codul tau ,din oricat threaduri:
		txTemplate.execute(status -> {
			//INSERT in tx noua
			return null;
		});
	}



	@Autowired 
	private OrderExporter exporter;
	
	// TODO [1] make singleton; multi-thread + mutable state = BAD
	// TODO [2] instantiate manually, set dependencies, pass around; no AOP
	// TODO [3] prototype scope + ObjectFactory or @Lookup. Did you said "Factory"? ...
	// TODO [4] thread/request scope. HOW it works?! Leaks: @see SimpleThreadScope javadoc




//	@Transactional merge pus aici
	public void run(String... args) {
		//tipic noi facem persist de date dummy
//		em.persist()

		exporter.export(Locale.ENGLISH);
		exporter.export(Locale.FRENCH);
		
	}
}
@Slf4j
@Service
@RequiredArgsConstructor
class OrderExporter  {
	private final InvoiceExporter invoiceExporter;
	private final LabelService labelService;

	public void export(Locale locale) {
		log.debug("Running export in " + locale);
		labelService.load(locale);
		log.debug("Origin Country: " + labelService.getCountryName("rO"));
		invoiceExporter.exportInvoice();
	}
}
@Slf4j
@Service
@RequiredArgsConstructor
class InvoiceExporter {
	private final LabelService labelService;

	public void exportInvoice() {
		log.debug("Invoice Country: " + labelService.getCountryName("ES"));
	}
}

@Slf4j
@Service
class LabelService {
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