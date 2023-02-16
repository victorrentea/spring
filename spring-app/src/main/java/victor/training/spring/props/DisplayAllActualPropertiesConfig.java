package victor.training.spring.props;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
@Slf4j

public class DisplayAllActualPropertiesConfig {
    @ConfigurationProperties // SPring, can you please inject *all* the properties
    // anyway you can in the Properties returned by this @Bean method
    @Bean
    public Properties allProps() {
        return new Properties();
    }

    @Bean
    public String test() {
        log.info("All props: \n" + allProps().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Object::toString)))
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining("\n")));
        return "DONE";
    }
}
@RequiredArgsConstructor
@Component
@Endpoint(id="resolved-props")
class AllPropsEndpoint {
    private final Properties allProps;

    @ReadOperation
    public Properties allProps() {
        return allProps;
    }
}