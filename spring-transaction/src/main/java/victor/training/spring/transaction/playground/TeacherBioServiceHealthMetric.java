package victor.training.spring.transaction.playground;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
public class TeacherBioServiceHealthMetric implements HealthIndicator {
  @Value("http://localhost:8082")
  private String teacherBioUriBase;

  private RestTemplate rest = new RestTemplate();

  @Override
  public Health health() {
    try {
        String url = teacherBioUriBase + "/actuator/health";
        Map<String, Object> responseMap = rest.getForObject(url, Map.class);
      // call localhost:8082/actuator/health to check {"status": "UP"}
      if (responseMap.get("status").equals("UP")) {
        return Health.up().build();
      }
    } catch (RestClientException e) {
      log.error("Health check failed to " + teacherBioUriBase + " :  " + e);
    }

//    return Health.down().build();
        return Health.up().status("SOMETHING REALLY BAD HAPPENED").build();
  }
}
