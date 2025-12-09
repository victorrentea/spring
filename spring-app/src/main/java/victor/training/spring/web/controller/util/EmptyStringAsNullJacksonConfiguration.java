package victor.training.spring.web.controller.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
// https://stackoverflow.com/a/53665706
public class EmptyStringAsNullJacksonConfiguration {

  @Bean
  SimpleModule emptyStringAsNullModule() {
    SimpleModule module = new SimpleModule();

    module.addDeserializer(
            String.class,
            new StdDeserializer<String>(String.class) {
              @Override
              public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                String result = StringDeserializer.instance.deserialize(parser, context);
                if (result == null || result.isEmpty() || result.isBlank()) {
                  return null;
                }
                return result;
              }
            });

    return module;
  }
}
