package victor.training.spring.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
     @Bean
     @ConfigurationProperties(prefix = "third")
    public ThirdPartyClass third() {
        return new ThirdPartyClass();
    }
}
