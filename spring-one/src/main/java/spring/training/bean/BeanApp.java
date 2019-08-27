package spring.training.bean;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import spring.training.resurse.PropertiesApp;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Bean
    public OldClass oldClass() {
        return OldClass.getInstance();
    }

    @Bean
    public NewCreepyClass newCreepyClass(OldClass oldClass) {
        System.out.println("creating NewCreepyClass");
        return new NewCreepyClass(oldClass, oldClass.getCurrentIp().split("\\."));
    }
    @Bean
    public SemiFinal semi1(NewCreepyClass newCreepyClass) {
        System.out.println("creating semi final1");
        return new SemiFinal(newCreepyClass);
    }
    @Bean
    public SemiFinal semi2(NewCreepyClass newCreepyClass) {
        System.out.println("creating semi final2");
        return new SemiFinal(newCreepyClass);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}


