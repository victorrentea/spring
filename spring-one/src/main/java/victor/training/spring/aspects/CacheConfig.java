package victor.training.spring.aspects;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching(order = 4)
public class CacheConfig {
}
