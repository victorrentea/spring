package victor.training.spring.web.controller.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module.SetupContext;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
// https://stackoverflow.com/a/53665706
public class FilterFieldsInResponseDtosJacksonConfig {

   @Bean
   SimpleModule filterResponseDtosModule() {
      return new SimpleModule() {
         @Override
         public void setupModule(SetupContext context) {
            super.setupModule(context);
            context.addBeanSerializerModifier(new BeanSerializerModifier() {
               @Override
               public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
                  if (!beanDesc.getBeanClass().getPackageName().startsWith("victor.training")) {
                     return beanProperties;
                  }
                  String propertyNamesCsv = beanProperties.stream().map(BeanPropertyWriter::getName).collect(Collectors.joining(","));
                  System.out.println("SERIALIZING DTO " + beanDesc.getBeanClass().getSimpleName() + ": " + propertyNamesCsv);
//                  return Collections.emptyList();
                  return beanProperties;
               }
            });
         }
      };
   }
}
