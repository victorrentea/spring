package victor.training.spring.props;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;


@Slf4j
@Component
@RefreshScope // what does this do?
public class HotReloadProperties {
  @Value("${dynamic.prop}")
  private String dynamicProp;
  // in spring the injection of dependencies NEVER REPEATS on the same instance
  // there is no 're-injection' possible => the only solution is to RE-CREATE (and inject) a new instance of this class

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
  private HotReloadProperties hotReloadProperties; // here, spring injects a PROXY
  private String ican;

  public void foolMansCache() {
    ican = hotReloadProperties.getDynamicProp(); // DONT
  }
  @GetMapping("dynamic-prop")
  public String dynamicProp() {
    return hotReloadProperties.getDynamicProp();
  }

}
// Experiment:
// 1) remove devtools from pom,
// 2) launch the app, go to http://localhost:8080/dynamic-prop
// 3) edit the target/application.properties
// 4) refresh the page => the same result
// 5) call the /actuator/refresh endpoint: curl -i -X POST http://localhost:8080/actuator/refresh
// 6) refresh the page => the updated property with no new banner in the console 🎉
