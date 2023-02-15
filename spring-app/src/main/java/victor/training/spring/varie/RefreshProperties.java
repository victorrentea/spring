package victor.training.spring.varie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Properties;


@Slf4j
@Component
@RefreshScope
public class RefreshProperties {
  @Value("${dynamic.prop}")
  private String dynamicProp;

  @PostConstruct
  public void onCreate() {
    log.info("Hello world! prop={} from {}", dynamicProp, this);
  }

  public String getDynamicProp() {
    return dynamicProp;
  }
}


// Experiment:
// 1) remove devtools from pom,
// 2) launch the app, go to http://localhost:8080/dynamic-prop
// 3) edit the target/application.properties
// 4) refresh the page => the same result
// 5) call the /actuator/refresh endpoint: curl -i -X POST http://localhost:8080/actuator/refresh
// 6) refresh the page => the updated property with no new banner in the console 🎉
