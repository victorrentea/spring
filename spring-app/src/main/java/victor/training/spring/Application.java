package victor.training.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import victor.training.spring.web.controller.util.TestDBConnectionInitializer;

import static java.lang.System.currentTimeMillis;

@EnableCaching(order = 20)
@EnableAspectJAutoProxy(exposeProxy = true) // allow to use AopContext.currentProxy()
@SpringBootApplication
@Slf4j
public class Application {
    public static final long t0 = currentTimeMillis();

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .listeners(new TestDBConnectionInitializer())
                .run(args);
    }

    @Autowired
    private Environment environment;

    @EventListener(ApplicationStartedEvent.class)
    @Order
    public void printAppStarted() {
        long t1 = currentTimeMillis();
        log.info("🎈🎈🎈 Application started on {} in {} millis 🎈🎈🎈", environment.getProperty("local.server.port"), t1-t0);
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/api/**")
//                        .allowCredentials(true) // allows receiving session cookie (if using cookies)
//                        .allowedOriginPatterns("http://localhost:8081/") // NodeJS
////					 .allowedOriginPatterns("http://*") // Too broad
//                ;
//                // also don't forget to add .cors() to spring security
//            }
//        };
//    }

}
