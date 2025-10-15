package victor.training.spring;

import io.micrometer.context.ContextSnapshot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

import static java.lang.System.currentTimeMillis;

@SpringBootApplication
@EnableCaching
@Slf4j
@ConfigurationPropertiesScan
public class RabitSpringApp {
  public static final long t0 = currentTimeMillis();

  public static void main(String[] args) {
      SpringApplication.run(RabitSpringApp.class, args);
  }

  @Autowired
  private Environment environment;

  @Bean // instrumented by micrometer-tracing
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @EventListener(ApplicationStartedEvent.class)
  @Order
  public void printAppStarted() throws SQLException {
    long t1 = currentTimeMillis();
    log.info("🎈🎈🎈 Application started in {}ms on port :{} 🎈🎈🎈",
        t1 - t0,
        environment.getProperty("local.server.port"));
  }

}
