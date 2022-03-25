package victor.training.spring.web.controller.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, MyEnum.class, MyEnum::valueOf);
        registry.addConverter(String.class, TrainingId.class, TrainingId::new);
    }
}
enum MyEnum {A,B}