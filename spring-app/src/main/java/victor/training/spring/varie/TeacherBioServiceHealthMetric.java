package victor.training.spring.varie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class TeacherBioServiceHealthMetric implements HealthIndicator {
    private static final Logger log = LoggerFactory.getLogger(TeacherBioServiceHealthMetric.class);
    @Value("${teacher.bio.uri.base}")
    private String teacherBioUriBase;
    @Override
    public Health health() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> responseMap = restTemplate.getForObject(teacherBioUriBase + "/actuator/health", Map.class);
            /// rest templte call localhost:8082/actuator/health
            // parse the JSON response as a Map
            // check that map.get("status").equlals"UP"
            if (responseMap.get("status").equals("UP")) {
                return Health.up().build();
            }
        } catch (RestClientException e) {
            log.error("Health check failed to " + teacherBioUriBase + " :  " + e);
        }

        return Health.down().build();
//        return Health.up().status("SOMETHING REALLY BAD HAPPENED").build();
    }
}
