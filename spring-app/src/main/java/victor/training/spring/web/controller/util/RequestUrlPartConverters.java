package victor.training.spring.web.controller.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.TrainingId;

import java.time.LocalDate;

@Configuration
public class RequestUrlPartConverters implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, ContractType.class, ContractType::valueOf);
//        registry.addConverter(String.class, TrainingId.class, id -> new TrainingId(id));
        registry.addConverter(String.class, LocalDate.class, LocalDate::parse);
    }
}
