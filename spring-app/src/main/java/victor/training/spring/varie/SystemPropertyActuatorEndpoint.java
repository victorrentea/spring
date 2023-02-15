package victor.training.spring.varie;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Endpoint(id = "system-property")
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
