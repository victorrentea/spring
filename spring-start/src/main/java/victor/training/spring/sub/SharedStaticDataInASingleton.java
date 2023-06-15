package victor.training.spring.sub;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SharedStaticDataInASingleton {
  private final Map<String, String> date = new ConcurrentHashMap<>();



//  @Scheduled(cron = "* * * 1 * *")
//  public void method() {
//    remoteDate = ... rest
//    date.clear;
//    date.putAll(remoteDate);
//  }


//  public void logica() {
//    date.get()
//  }
}
