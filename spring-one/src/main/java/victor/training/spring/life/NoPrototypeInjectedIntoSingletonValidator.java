package victor.training.spring.life;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
public class NoPrototypeInjectedIntoSingletonValidator implements BeanPostProcessor {
   private final ConfigurableListableBeanFactory beans;

   public NoPrototypeInjectedIntoSingletonValidator(ConfigurableListableBeanFactory beans) {
      this.beans = beans;
   }

   @Override
   public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      String hostScopeName = beans.getBeanDefinition(beanName).getScope();
      log.info("Bean {} has scope {}", beanName, hostScopeName);

      if (!hostScopeName.equals("singleton")) {
         return bean; // nothing to do
      }
      for (Field field : bean.getClass().getDeclaredFields()) {
         field.setAccessible(true);
         try {
            Object dependency = field.get(bean);
            if (dependency == null) {
               continue;
            }
            Scope annotation = AnnotationUtils.findAnnotation(dependency.getClass(), Scope.class);
            if (annotation == null) {
               continue;
            }
            if (annotation.value().equals("prototype")) {
               throw new IllegalArgumentException("The bean '" + beanName + "' of type " + bean.getClass().getCanonicalName() + " has a dependency in the field '" + field.getName() + "' to a prototype scoped bean of type " + dependency.getClass().getCanonicalName());
            }
         } catch (IllegalAccessException e) {
            log.warn("Cannot check bean {}: {}", beanName, e.getCause());
            log.trace(e.getMessage(), e);
         }
      }
      return null;
   }
}
