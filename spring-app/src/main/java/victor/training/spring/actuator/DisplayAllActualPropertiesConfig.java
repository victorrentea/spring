package victor.training.spring.actuator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import victor.training.spring.actuator.DisplayAllActualPropertiesConfig.ActualPropertiesActuatorEndpoint;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.stream.Collectors;

//@Configuration // uncomment to play
@Slf4j
@Import(ActualPropertiesActuatorEndpoint.class) // aka @Bean X  {return new X()}
public class DisplayAllActualPropertiesConfig {
  @ConfigurationProperties
  @Bean
  public Properties allProps() {
    return new Properties();
  }

  @Endpoint(id = "actual-properties") // http://localhost:8080/actuator/actual-properties
  @RefreshScope // reload at /refresh
  @RequiredArgsConstructor
  public static class ActualPropertiesActuatorEndpoint {
    private final Properties allProps;

    @PostConstruct
    public void printResolvedProps() {
      log.info("All props: \n" + allProps.entrySet()
              .stream()
              .sorted(Map.Entry.comparingByKey(Comparator.comparing(Object::toString)))
              .map(e -> e.getKey() + " = " + e.getValue())
              .collect(Collectors.joining("\n")));
    }

    @ReadOperation
    @SuppressWarnings("rawtypes")
    public Map<String, String> getAllProps() {
      return new TreeMap<>((Map<String, String>) (Map) allProps);
    }
  }

}
