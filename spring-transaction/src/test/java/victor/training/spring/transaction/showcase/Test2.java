package victor.training.spring.transaction.showcase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@SpringBootTest
@Import({Test1.One.class, Test2.One.class})
public class Test2 {
    @TestConfiguration
    static class MyConfig {

        @Bean
        public static BeanDefinitionRegistryPostProcessor method() {
            return new BeanDefinitionRegistryPostProcessor() {
                @Override
                public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                    for (Class<?> declaredClass : Test2.class.getNestMembers()) {
                        System.out.println("Found " + declaredClass);
                    }
//                for (int i = 0; i < 50; i++) {
//                    BeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(Minion.class).addConstructorArgValue("Minion " + i).getBeanDefinition();
//                    registry.registerBeanDefinition("bean-" + i, definition);
//                }
                }

                @Override
                public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

                }
            };
        }
    }

    @Component
    public static class One {
    }

    @Component
    public static class Two {
    }


    @Autowired
    One one;

    @Test
    void explore() {
        System.out.println(one.getClass());
    }
}
