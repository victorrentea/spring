package com.example.demo;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.core.aop.TimedAspect;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

//@EnableCaching(order = 2)
@EnableConfigurationProperties(Config.class)
@SpringBootApplication
//@EnableScheduling
@EnableAsync
@EnableCaching
@RequiredArgsConstructor
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
	public Caffeine caffeineConfig() {
		return Caffeine.newBuilder()
				.expireAfterWrite(3, TimeUnit.SECONDS);
	}

	@Bean
	public CacheManager cacheManager(Caffeine caffeine) {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.setCaffeine(caffeine);
		return caffeineCacheManager;
	}

	@Bean // definesc programatic beanuri
	public TimedAspect timedAspect() {
    return new TimedAspect();
	}

	private final UserRepository userRepository;
	@EventListener(ApplicationStartedEvent.class)
	public void insertData(){
		for (int i = 0; i < 10; i++) {
			userRepository.save(new User()
					.setEmail("email"+i+"@db.com")
					.setName("name"+i));
		}
		System.out.println(userRepository.findAll());
	}

	@Bean
	@ConfigurationProperties(prefix = "pool")
	public ThreadPoolTaskExecutor exportPool(/*@Value("${pool.size}")
																						 int size*/){ // numele beanului = numele metodei
    //		executor.setCorePoolSize(size);
		return new ThreadPoolTaskExecutor();
	}
}
