package victor.training.spring.web.controller.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@ConditionalOnMissingBean(name = "bang")
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, TrainingId.class, TrainingId::new);
        registry.addConverter(String.class, MyEnum.class, MyEnum::valueOf);
    }
}
enum MyEnum {A,B}