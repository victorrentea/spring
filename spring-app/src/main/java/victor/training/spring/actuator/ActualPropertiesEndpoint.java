package victor.training.spring.actuator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import victor.training.spring.actuator.ActualPropertiesEndpoint.ActuatorEndpoint;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

@Configuration
@Slf4j
@Import(ActuatorEndpoint.class) // aka @Bean X  {return new X()}
public class ActualPropertiesEndpoint {
    @ConfigurationProperties // without prefix to get all properties
    @Bean
    public Properties allProps() {
        return new Properties();
    }

    @Endpoint(id = "actual-properties")
    // http://localhost:8080/actuator/actual-properties
    @RefreshScope // reload at http://localhost:8080/actuator/refresh
    @RequiredArgsConstructor
    public static class ActuatorEndpoint {
        private final Properties allProps;

        @PostConstruct
        public void printResolvedProps() throws JsonProcessingException {
            TreeMap<String, String> sortedProps = new TreeMap<>((Map<String, String>) (Map) allProps);
            String allPropsJson = new ObjectMapper().writeValueAsString(sortedProps);
            log.info("All properties: " + allPropsJson);
        }

        @ReadOperation
        @SuppressWarnings("rawtypes")
        public Map<String, String> getAllProps() {
            return new TreeMap<>((Map<String, String>) (Map) allProps);
        }
    }

}
