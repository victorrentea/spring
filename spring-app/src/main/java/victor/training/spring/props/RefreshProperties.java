package victor.training.spring.props;

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

@RestController
class DynamicPropController {
  @Autowired
  private RefreshProperties refreshProperties;

  @GetMapping("dynamic-prop")
  public String dynamicProp() {
    return refreshProperties.getDynamicProp();
  }
}

@Component
@Endpoint(id="system-property")
class SystemPropertyActuatorEndpoint {
  @WriteOperation
  public void setProperty(@Selector String property, String value) {
    System.setProperty(property, value);
  }
  @ReadOperation
  public String getProperty(@Selector String property) {
    return System.getProperty(property);
  }
  @ReadOperation
  public Properties getAll() {
    return System.getProperties();
  }
}
// Experiment:
// 1) remove devtools from pom,
// 2) launch the app, go to http://localhost:8080/dynamic-prop
// 3) edit the target/application.properties
// 4) refresh the page => the same result
// 5) call the /actuator/refresh endpoint: curl -i -X POST http://localhost:8080/actuator/refresh
// 6) refresh the page => the updated property with no new banner in the console 🎉
