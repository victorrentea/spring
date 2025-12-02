package victor.training.spring.first;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import victor.training.spring.X;

@Configuration//(proxyBeanMethods = false)
public class AltConfig {
  @Bean
//  public AltBeanCreatDeMana unu(X x) { // ❤️
  public AltBeanCreatDeMana unu() {
    System.out.println("unu");
    return new AltBeanCreatDeMana(x());
  }
  @Bean
  public AltBeanCreatDeMana two() {
    System.out.println("doi");
    return new AltBeanCreatDeMana(x()); // aici x() e o 'referinta'
    // apelul local de @Bean method este by default INTERCEPTAT. nu ca la @Transactiona & friends
    // - intr-un @Configuration clasa instantiata in prod este subclasa efectiva de AltConfig
    // - in orice alt loc in SPring, se instantiaza separat 1) CGLIBproxy -> 2) bean real
  }
  // apelurile locale de metode NU sunt by default interceptate
  // DECAT intre @Bean methods dintr-un @Configuration care n-are (proxyBeanMethods = false)
  @Bean
  // 1) clasa X e dintr-o lib: ex ThreadPoolTaskExecutor
  // 2) cand vrei 2+ instante cu acelasi tip
  public X x() {
    System.out.println("CALL✅");
    return new X();
  }
}
//  @Scope("prototype")/"request"
