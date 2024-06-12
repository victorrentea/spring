package victor.training.spring.aspects;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.TimeUnit;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class ProxySpringApp {
  public static void main(String[] args) {
    SpringApplication.run(ProxySpringApp.class, args);
  }

  @Bean
  public CacheManager cacheManager(Caffeine caffeine) {
    CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
    caffeineCacheManager.setCaffeine(caffeine);
    return caffeineCacheManager;
  }
//
  @Bean
  public Caffeine caffeineConfig() {
    return Caffeine.newBuilder()
        .maximumSize(4)
        .expireAfterWrite(1, TimeUnit.HOURS) // TTL
        ;
  }


//    @Autowired // nu merge @Cacheable intre SecondGrade si Maths pentru ca
  // flxuul pornit din @Autowired sau @PostConstruct e PREA DEVREME
  // si proxy-urile nu sunt gata.
//    public void run(SecondGrade secondGrade) {
  @Autowired
  private SecondGrade secondGrade;

  @EventListener(ApplicationStartedEvent.class) // ruleaza mai tarziu
  // cand beanurile au proxyurile pe ele.
  public void run() {
    System.out.println("Running Maths class...");
    secondGrade.mathClass();
  }
}

// cum fac un proxy care sa intercepteze
// apeluri de metode
// intre beanurile Spring din
// pachetul victor..
// cu @Around
