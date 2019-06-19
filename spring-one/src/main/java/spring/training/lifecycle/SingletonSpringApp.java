package spring.training.lifecycle;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.stereotype.Service;

@EnableCaching
@SpringBootApplication
public class SingletonSpringApp implements CommandLineRunner{
	@Bean
	public static CustomScopeConfigurer defineThreadScope() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		configurer.addScope("thread", new SimpleThreadScope()); // WARNING: Leaks memory. Prefer 'request' scope or read here: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/SimpleThreadScope.html
		return configurer;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SingletonSpringApp.class);
	}
	
	@Autowired 
	private OrderExporter exporter;
	
	// TODO [1] make singleton; test multi-thread: state is [e|v|i|l]
	// TODO [2] instantiate manually, set dependencies, pass around; no AOP
	// TODO [3] prototype scope + ObjectFactory or @Lookup. Did you said "Factory"? ...
	// TODO [4] thread/request scope. HOW it works?! Leaks: @see SimpleThreadScope javadoc
	// TODO [5] (after AOP): RequestContext, @Cacheable. on thread?! @ThreadLocal
	public void run(String... args) throws Exception {
		new Thread(() -> exporter.export(Locale.ENGLISH)).start();
		new Thread(() -> exporter.export(Locale.FRENCH)).start();
		
	}
	
	@Autowired
	private Singleton1 s1;
	@Autowired
	private Singleton1 s1Prim;
}


// TODO FIXME XXX joacate aici !!


@Service
class Singleton1 {
	@Autowired
	private Prototype1 p1;
	
	@Autowired
	private Prototype1 p1Prim;
	
	public Singleton1() {
		System.out.println("+Singleton1");
	}
}

@Service
@Scope("prototype")
class Prototype1 {
	
	@Autowired 
	private Prototype2 p2;
	@Autowired 
	private Prototype2 p2Prim;
	@Autowired
	private XX x;
	public Prototype1() {
		System.out.println("+Prototype1");
	}
}
@Service
@Scope("prototype")
class Prototype2 {
	public Prototype2() {
		System.out.println("+Prototype2");
	}
}
@Service
class XX {
	public XX() {
		System.out.println("+XX");
	}
	
}



@Service
class OrderExporter  {
	private final static Logger log = LoggerFactory.getLogger(OrderExporter.class);
	@Autowired
	private InvoiceExporter invoiceExporter;
	@Autowired
	private LabelService labelService;

	public void export(Locale locale) {
		System.out.println("hash-ul instantei "  +labelService.hashCode());
		System.out.println("Oare cu cine vorbesc??! " + labelService.getClass());
		labelService.load(locale);
		log.debug("Running export in " + locale);
		log.debug("Origin Country: " + labelService.getCountryName("rO")); 
		invoiceExporter.exportInvoice();
	}
}

@Service 
class InvoiceExporter {
	private final static Logger log = LoggerFactory.getLogger(InvoiceExporter.class);
	@Autowired
	private LabelService labelService;
	
	public void exportInvoice() {
		log.debug("Invoice Country: " + labelService.getCountryName("ES"));
	}
}

@Service
@Scope(value = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
class LabelService {
	private final static Logger log = LoggerFactory.getLogger(LabelService.class);
	private CountryRepo countryRepo;
	
	public LabelService(CountryRepo countryRepo) {
		System.out.println("+1 Label Service");
		this.countryRepo = countryRepo;
	}

	private Map<String, String> countryNames;
	
	public void load(Locale locale) {
		log.debug("Incarc pe instanta de LabelService: " + this.hashCode());
		countryNames = countryRepo.loadCountryNamesAsMap(locale);
	}
	
	public String getCountryName(String iso2Code) {
		log.debug("getCountryName() on instance: " + this.hashCode());
		return countryNames.get(iso2Code.toUpperCase());
	}
}