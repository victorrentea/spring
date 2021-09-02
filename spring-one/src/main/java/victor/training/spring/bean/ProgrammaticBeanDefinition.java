package victor.training.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ProgrammaticBeanDefinition implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(ProgrammaticBeanDefinition.class, args);
   }

   @Bean
   public static BeanDefinitionRegistryPostProcessor method() {
      return new BeanDefinitionRegistryPostProcessor() {
         @Override
         public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            for (int i = 0; i < 50; i++) {
               BeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(Minion.class).addConstructorArgValue("Minion " + i).getBeanDefinition();
               registry.registerBeanDefinition("bean-" + i, definition);
            }
         }
         @Override
         public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

         }
      };
   }

   @Autowired
   private List<Minion> minions;
   @Override
   public void run(String... args) throws Exception {
      System.out.println("Registered minions: " + minions);
   }
}
class Minion {
   private final String name;


   Minion(String name) {
      this.name = name;
   }

   @Override
   public String toString() {
      return "Minion{" +
             "name='" + name + '\'' +
             '}';
   }
}