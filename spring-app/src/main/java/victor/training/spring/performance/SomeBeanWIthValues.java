package victor.training.spring.performance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

// 0: push on config git > config server gives new values when asked
// 1: /actuator/refresh on all pods
// 2: spring download config from config server
// 3: spring destroys/recreates beans with @RefreshScope
// faster than restarting PODs
@RefreshScope
@Component
public class SomeBeanWIthValues {
//  @Value("${prop.from.config.server}")
//  String prop;

  public void method() {

  }
}
