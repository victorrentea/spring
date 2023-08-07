package victor.training.spring.first;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.*;

import java.util.HashSet;
import java.util.Set;

//@Configuration
public class CollidingPropertyChecker implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment environment = (ConfigurableEnvironment) beanFactory.getBean(ConfigurableEnvironment.class);
        Set<String> seenProperties = new HashSet<>();

        for (String activeProfile : environment.getActiveProfiles()) {
            for (PropertySource<?> propertySource : environment.getPropertySources()) {
                if (propertySource instanceof EnumerablePropertySource) {
                    EnumerablePropertySource<?> enumerablePropertySource = (EnumerablePropertySource<?>) propertySource;
                    for (String propertyName : enumerablePropertySource.getPropertyNames()) {
                        if (!seenProperties.add(propertyName)) {
                            throw new IllegalStateException("Colliding property '" + propertyName + "' detected in profile '" + activeProfile + "'.");
                        }
                    }
                }
            }
        }
    }
}