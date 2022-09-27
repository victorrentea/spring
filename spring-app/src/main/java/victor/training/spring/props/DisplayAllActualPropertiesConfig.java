package victor.training.spring.props;

import org.slf4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
public class DisplayAllActualPropertiesConfig {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DisplayAllActualPropertiesConfig.class);

    @ConfigurationProperties
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
