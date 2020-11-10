package victor.training.spring.web;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableCaching
public class SpaApplication {

   public static void main(String[] args) {
      new SpringApplicationBuilder(SpaApplication.class)
          .profiles("spa")
          .run(args);
   }

//   @Bean
//   public Config configuration() {
//      Config config = new Config();
//      config.setInstanceName("hazelcast-instance");
//      MapConfig mapConfig = new MapConfig();
//      mapConfig.setName("configuration");
////      mapConfig.setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE));
////      mapConfig.setEvictionPolicy(EvictionPolicy.LRU);
//      mapConfig.setTimeToLiveSeconds(-1);
//      config.addMapConfig(mapConfig);
//      return config;
//   }

}
