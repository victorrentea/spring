package victor.training.spring.web.controller.util;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Configuration
public class SearchForRestControllerAdviceBeans_forDaniel {
//    @Bean
    public BeanPostProcessor method() {
//        System.out.println("BPP in");
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//                if (bean.isannotated with @RestController) all the public methods should carry a @Secured

                System.out.println("BEAN CLASS: " + bean.getClass());
                if (bean.getClass().isAnnotationPresent(RestControllerAdvice.class)) {
                    System.out.println("FOUND a RCA: " + beanName + " for " + bean.getClass());
                }
                return bean;
            }
        };
    }
}
