package victor.training.spring.first.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import victor.training.spring.first.Z;

@Configuration
public class ZConfig {
    @Bean
    public Z z() {
        return new Z();
    }
}
