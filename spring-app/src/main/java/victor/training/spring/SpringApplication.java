package victor.training.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.web.controller.util.TestDBConnectionInitializer;

import javax.sql.DataSource;

import java.sql.SQLException;

import static java.lang.System.currentTimeMillis;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@Slf4j
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

    @Autowired
    private DataSource dataSource;

    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }

    @EventListener(ApplicationStartedEvent.class)
    @Order
    public void printAppStarted() throws SQLException {
        long t1 = currentTimeMillis();
        String jdbcUrl = dataSource.getConnection().getMetaData().getURL();
        log.info("🎈🎈🎈 Application started in {}ms on port :{} connected to DB {} 🎈🎈🎈",
                t1-t0,
                environment.getProperty("local.server.port"),
                jdbcUrl);
    }

    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) {
                log.info("🎈🎈🎈 Bean {} created 🎈🎈🎈", beanName);

                // if the class is marked with @RestController, all public methods should be annotated with @Secured
                // otherwise throw an exception
                if (bean.getClass().isAnnotationPresent(RestController.class)) {
                    for (var method : bean.getClass().getDeclaredMethods()) {
                        if (method.isAnnotationPresent(Secured.class)) {
                            log.info("🎈🎈🎈 Method {} is secured 🎈🎈🎈", method.getName());
                        } else {
                            throw new RuntimeException("Method " + method.getName() + " is not secured");
                        }
                    }
                }




                return bean;
            }
        };
    }

}
