package victor.training.spring.web.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

public interface ServiceInterface {
    @Transactional
    int stuff(int x);
}
