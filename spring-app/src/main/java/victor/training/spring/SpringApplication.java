package victor.training.spring;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.controller.util.TestDBConnectionInitializer;

import static java.lang.System.currentTimeMillis;

@SpringBootApplication
@EnableCaching
@Slf4j
@EnableFeignClients
@ConfigurationPropertiesScan
public class SpringApplication {
    public static final long t0 = currentTimeMillis();

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringApplication.class)
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
