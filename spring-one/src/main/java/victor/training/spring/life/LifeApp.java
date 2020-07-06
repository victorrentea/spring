package victor.training.spring.life;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(scopedProxy = ScopedProxyMode.TARGET_CLASS)
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
      configureBeans();

      new Thread(() -> exporter.export(Locale.ENGLISH)).start();
      new Thread(() -> exporter.export(Locale.FRENCH)).start();

   }

   @Bean
   public RestTemplate restTemplate() {
      return new RestTemplate();
   }
//	@Bean
//	public WSClient fbClient(@Autowired RestTemplate restTemplate) {
//		return new WSClient("fb.com",9989, restTemplate);
//	}
//	@Bean
//	public WSClient linkedInClient(@Autowired RestTemplate restTemplate) {
//		return new WSClient("linkedin.com",9989, restTemplate);
//	}

   @Autowired
   ConfigurableListableBeanFactory beanFactory;

//   @PostConstruct
   public void configureBeans() {
      for (int i = 0; i < 3; i++) {
         beanFactory.registerSingleton("wsClientBean"+i,
             new WSClient("host" + i, i, restTemplate()));
      }
   }

}
@Data
class WSClient {
   private final String host;
   private final int port;
   private final RestTemplate restTemplate;
}


@Slf4j
@Service
@RequiredArgsConstructor
class OrderExporter {
   private final InvoiceExporter invoiceExporter;
   private final ILabelService labelService;

   public void export(Locale locale) {
      try {
         log.debug("Cu cine mama masii vobesc eu aici, de-mi ruleaza pe diferite instante, nu doar una: "
             + labelService.getClass());
         log.debug("Running export in " + locale);
         labelService.load(locale);
         log.debug("Origin Country: " + labelService.getCountryName("rO"));
         invoiceExporter.exportInvoice();
      } finally {
         // TODO clear thread scope ca altfel: memleak si data leak
      }
   }
}

@Slf4j
@Service
@RequiredArgsConstructor
class InvoiceExporter {
	private final ILabelService labelService;

   public void exportInvoice() {
      log.debug("Invoice Country: " + labelService.getCountryName("ES"));
   }
}

@Slf4j
@Service
@Scope(scopeName = "thread")
class LabelService implements ILabelService{
   private final CountryRepo countryRepo;

   public LabelService(CountryRepo countryRepo) {
      System.out.println("+1 Label Service: " + this.hashCode());
      this.countryRepo = countryRepo;
   }

   private Map<String, String> countryNames;

   @Override
   public void load(Locale locale) {
      log.debug("LabelService.load() on instance " + this.hashCode());
      countryNames = countryRepo.loadCountryNamesAsMap(locale);
   }

   @Override
   public String getCountryName(String iso2Code) { // seamana a getter
      log.debug("LabelService.getCountryName() on instance " + this.hashCode());
      return countryNames.get(iso2Code.toUpperCase());
   }
}