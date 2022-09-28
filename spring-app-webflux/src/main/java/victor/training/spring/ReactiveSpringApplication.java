package victor.training.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.web.controller.util.TestDBConnectionInitializer;
import victor.training.spring.web.service.TrainingService;

import static java.lang.System.currentTimeMillis;



@EnableAsync
@EnableCaching
@SpringBootApplication
@Import(TrainingService.class)
@Slf4j
public class ReactiveSpringApplication {
    public static final long t0 = currentTimeMillis();

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactiveSpringApplication.class)
                .listeners(new TestDBConnectionInitializer())
                .run(args);
    }

    @Autowired
    private Environment environment;

    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }

    @EventListener(ApplicationStartedEvent.class)
    @Order
    public void printAppStarted() {
        long t1 = currentTimeMillis();
        log.info("🎈🎈🎈 Application started on port: {} connected to DB: {} in {} millis 🎈🎈🎈",
                environment.getProperty("local.server.port"),
                environment.getProperty("spring.datasource.url"),
                t1-t0);
    }

}
