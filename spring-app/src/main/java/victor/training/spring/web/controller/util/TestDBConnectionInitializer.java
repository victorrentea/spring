package victor.training.spring.web.controller.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

@Slf4j
public class TestDBConnectionInitializer implements ApplicationListener<ApplicationContextInitializedEvent> {
   public static void assertCanConnectToDB(Environment env) {
      String url = env.getRequiredProperty("spring.datasource.url");
      try {

//         ConfigurableApplicationContext c;
//         c.getBeanFactory().regi
         Class.forName(env.getRequiredProperty("spring.datasource.driver-class-name"));
         log.debug("Trying to connect to {}", url);
         Connection connection = DriverManager.getConnection(url,
             env.getRequiredProperty("spring.datasource.username"),
             env.getRequiredProperty("spring.datasource.password"));

         log.info("Connection Established Successfully to {} database", connection.getMetaData().getDatabaseProductName());
         connection.close();
      } catch (Exception e) {
         log.warn("Cannot connect to database {}. Cause: {}", url, e.getMessage());
         throw new IllegalStateException("Could not connect to database: " + url+"\nHint: Did you started StartDatabase.java?", e);
      }
   }

   @Override
   public void onApplicationEvent(ApplicationContextInitializedEvent event) {
      assertCanConnectToDB(event.getApplicationContext().getEnvironment());
      log.info("Database connection checked OK");
   }
}
