package victor.training.spring.props;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.FeaturesActuatorEndpoint.FeatureFlag;

import java.util.*;

@Component
@Endpoint(id = "features-flags") // https://www.baeldung.com/spring-boot-actuators
public class FeaturesActuatorEndpoint {
  public enum FeatureFlag {
    DISPLAY_POST_VIEWS,
    DISPLAY_UNLIKE_BUTTON
  }

  @Value("${feature-flags}")
  private Set<FeatureFlag> activeFeatures = Collections.synchronizedSet(new TreeSet<>());

  public boolean isActive(FeatureFlag featureFlag) {
    return activeFeatures.contains(featureFlag);
  }

  @ReadOperation
  public Set<FeatureFlag> getAllActiveFeatures() {
    return activeFeatures;
  }

  @ReadOperation // http://localhost:8080/actuator/features-flags
  public boolean feature(@Selector FeatureFlag featureFlag) {
    return isActive(featureFlag);
  }

  @WriteOperation // curl -X POST http://localhost:8080/actuator/features-flags/DISPLAY_POST_VIEWS
  public void activateFeature(@Selector FeatureFlag featureFlag) {
    activeFeatures.add(featureFlag);
  }

  @DeleteOperation // curl -X DELETE http://localhost:8080/actuator/features-flags/DISPLAY_POST_VIEWS
  public void disableFeature(@Selector FeatureFlag featureFlag) {
    activeFeatures.remove(featureFlag);
  }
}

@RequiredArgsConstructor
@RestController
class SomeEndpoint {
  private final FeaturesActuatorEndpoint features;

  @GetMapping("variable-endpoint") // http://localhost:8080/variable-endpoint
  public String getVariableEndpoint() {
    String result = "POST ABC ";
    if (features.isActive(FeatureFlag.DISPLAY_POST_VIEWS)) {
      result += " (views=73)";
    }
    return result;
  }
}