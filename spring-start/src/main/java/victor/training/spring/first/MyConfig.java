package victor.training.spring.first;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@ComponentScan#1 not in Picnic

@Configuration
@ConditionalOnMissingBean(MailService.class) // on @Configuration only
//@Import(MailServiceImpl.class) // #2
public class MyConfig {
  @Bean // #3 manually create a class, usually NOT your types
  // for example to define a Caffeine cache in your app
  public MailService impl() {
    return new MailServiceImpl();// not to just "new"
  }

//  @Bean
//  Caffeine <Object, Object> caffeineConfig() {
//    return Caffeine.newBuilder()
//        .initialCapacity(20)
//        .maximumSize(100)
//        .recordStats();
//  }

  //@Bean
  //	public ThreadPoolTaskExecutor poolBar() {
  //		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
  //		executor.setCorePoolSize(1);
  //		executor.setMaxPoolSize(1);
  //		executor.setQueueCapacity(500);
  //		executor.setThreadNamePrefix("pool-bar-");
  //		executor.initialize();
//}
}
